import React, { createContext, useCallback, useContext, useEffect, useState } from "react"
import { Button, Col, Offcanvas, OffcanvasBody, OffcanvasHeader, OffcanvasTitle, Row } from "react-bootstrap"
import useWindowsizes from "../../components/Windowsize";
import { request } from "../../components/variables/variables";
import useGetrequests from "../../components/customRequests/getrequest";
import { ToastContainer, toast } from "react-toastify"
import 'react-toastify/dist/ReactToastify.css'
import { errorMessage, successMessage, validateInput } from "../../components/methods/computations";
import UserInfoContext from "../../components/Header/userInfoContext";
import { useNavigate } from "react-router-dom";


const Register=(props)=>{
    // const   [userinfo,setUserinfo] = useContext(UserInfoContext);
    const  [isLoggedins,setIsLoggeds]= useState(false)
    const {isSmall,setSmall}=useWindowsizes();
    const info = props;
    const [user,setUser] = useState({
        "firstname":null,
        "lastname":null,
        "email":null,
        "phone":null,
         "dob" :null,
        "gender":null,
        "password":null
    });
    const [token,setToken]=useState(null);
    const [userDetail,setUserDetail] =useContext(UserInfoContext)
    const navigate = useNavigate()

    /**
     * called once during refresh
     * called again after the value of token has been updated by the generate token method
     * this gets the cart id for the user 
     * 
     */
    useEffect(()=>{
        //.log("inside ue of register",props,isLoggedins)
        // setIsLogged(false);
       // console.log(token,user.phone,isLoggedins)
       
        if(token!=null){
            setIsLoggeds(true);
           // console.log("success-> calling gettoken",isLoggedins)
            getCartId(user.phone)
        }
      
    },[token])

    useEffect(()=>{

        if(token!=null){
               // console.log("closing..")
               
                info.onHide();
        }
    },[user])

    // useEffect(()=>{
    //     generateTokens() 
    // },[user])

    const currentTime = new Date().getTime();

    // Calculate the delay in milliseconds -30 minutes
    const delay = 1000 * 60 * 29;

    // Define a callback function to clear the local storage
    const clearLocalStorage = ()=> {
        errorMessage("session expired! login again to continue")
        localStorage.clear();
        setIsLoggeds(false)
      //  console.log("Local storage cleared");
        setUserDetail( {
          "firstname":null,
          "lastname":null,
          "email":null,
          "phone":null,
           "dob" :null,
          "gender":null,
          "password":null,
          "token":null,
          "isLogged":false
      })
        navigate('/home')
    };


    /**
     * 
     * @param {phone number of the user} phone 
     * the cartid is obtained for the registeres user 
     * if the user is new and doesn;t have cartid-> value is not set
     * else local storage value of cart id is set to the users cart id
     * 
     */
    const getCartId=(phone)=>{
       // console.log("inside getcartId",isLoggedins)
        request
        .get(
            `getCartId`,
            {
                headers:{
                    "Content-Type":"application/json",
                    "Access-Control-Allow-Header":"Content-Type",
                    "Access-Control-Allow-Origin": "*",
                    "Authorization" : `Bearer ${token}`
                }

            })
        .then((response)=>{
                if(response.status===200){
               //     console.log("resp",response.data);
                    localStorage.setItem("cartId",response.data)
                  //  console.log("response done success");
                }
            })
        .catch(error=>{
            // setError(error)
          //  console.log(error)
            errorMessage(error)
        });

    }

    /**
     * Called by createUser, after the cal is successfully completed
     * obtains the token bt calling the API : woodland/generateTokens
     * Sets the local storage values -> 
     * phone: set to user's phone
     * isLoggedin : set to true
     * usersphone_value-> set to the token
     * sets the state value of token to returnd token
     * 
     */

    const generateTokens=()=>{
       // console.log("inside the generate tokens method-------",user)
        request
        .post('/generateTokens', 
            {               
                "phone":user.phone,
                "password":user.password              
            },
            {
                headers:{
                    "Content-Type":"application/json",
                    "Access-Control-Allow-Header":"Content-Type",
                    "Access-Control-Allow-Origin": "*",
                }

            })
        .then((response)=>{
           // console.log("response data:",response.data)
            if(response.status==200){
               // console.log("response ",response)
                localStorage.setItem("isLoggedin",true)
                localStorage.setItem(user.phone,response.data)
                localStorage.setItem('phone',user.phone)
                 setToken(response.data);
                 setUserDetail({
                 ...userDetail,
                 "token":response.data
                })
                setTimeout(props.onHide,1000)
                setTimeout(clearLocalStorage, delay);
               // getCartId(user.phone)
               
                // successMessage();
            }
           
        })
        .catch((error)=>{
           // console.log("error----->",error);
        });
    }
    

    /**
     * function aftr the validate function succeeds
     * name:= createUser()
     * calls the UPI  woodland/register
     * logs in / registes the user , 
     * calls the generatetoken method internally
     */
    const createUser=()=>{
        //.log("inside the methhod-------",user)
        request
        .post('/register', 
            {               
                "phone":user.phone,
                "password":user.password              
            },
            {
                headers:{
                    "Content-Type":"application/json",
                    "Access-Control-Allow-Header":"Content-Type",
                    "Access-Control-Allow-Origin": "*",

                }

            })
        .then((response)=>{
            // console.log("response data:",response.data)
            if(response.status==200){
                setUser(response.data);
                setUserDetail({
                  "firstname":response.data.firstname
                })
                localStorage.setItem('name',response.data.firstname)
                // console.log("userpost: ",user)
                successMessage("Logged in successfully")
                generateTokens() ;
                //localStorage.setItem('name','guest')
            }
            
           
        })
        .catch((error)=>{
           // console.log("error----->",error);
            errorMessage(error.response.data)
            // if(error.response.status==500){
            //     errorMessage(error.response.data)
            // }
        });
    }
    
    /**
     * The fisrt function called after clicking on login
     * if validation succeeds : createUser() funciton is called
     * else: display an error messsgae
     */
    const validateInputFields=()=>{
      if(validateInput("password",user.password) && validateInput("phone",user.phone)){
        createUser();
      }
      else if(!validateInput("phone",user.phone) ){
        errorMessage("Invalid phone number")
      }
      else{
        errorMessage("Password criteria not matched! ")
      }

    }



    const offcanvastyle={
        "--bs-offcanvas-width": isSmall<900? "100%" : "45%"
    }

    return (
      <React.Fragment>
        <Offcanvas style={offcanvastyle} {...info} placement="end">
          <Row style={{ padding: "3%" }}>
            <Col xs={2}>
              <OffcanvasHeader closeButton></OffcanvasHeader>
            </Col>
            <Col xs={10}>
              <OffcanvasTitle
                style={{
                  color: "rgba(48, 48, 48, 0.92)",
                }}
              >
                Login / Register
              </OffcanvasTitle>
              <p
                tyle={{
                  color: "rgb(37, 16, 0)",
                }}
              >
                For a personalized experience
              </p>
            </Col>
            <hr></hr>
          </Row>
          <ToastContainer />

          <OffcanvasBody style={{ textAlign: "left", padding: "10%" }}>
            <p>
              <b>Login/Register</b> with your mobile number
            </p>
            <form
              onSubmit={(e) => {
                e.preventDefault();
                e.target[0].value = "";
                e.target[1].value = "";
                validateInputFields();
              //  console.log("form calling.....", e);
              }}
            >
              <div
                style={{
                  margin: "1%",
                }}
              >
                <input
                  type="number"
                  value={user.phone}
                  onChange={(e) => {
                    setUser({
                      ...user,
                      phone: e.target.value,
                    });
                  }}
                  style={{
                    width: "100%",
                    backgroundColor: "rgb(244, 244, 244)",
                    fontSize: "13px",
                    color: "rgb(123,123,123)",
                    padding: "10px",
                    border: "0px",
                    margin: "2% 0px",
                  }}
                  placeholder="Enter 10 digit mobile number"
                />

                <input
                  value={user.password}
                  onChange={(e) => {
                    setUser({
                      ...user,
                      password: e.target.value,
                    });
                  }}
                  type="password"
                  style={{
                    width: "100%",
                    backgroundColor: "rgb(244, 244, 244)",
                    fontSize: "13px",
                    color: "rgb(123,123,123)",
                    padding: "10px",
                    border: "0px",
                  }}
                  placeholder="Enter password"
                />
              </div>

              <p
                style={{
                  margin: "3% 1%",
                  fontSize: "12px",
                }}
              >
                <input
                  style={{
                    marginRight: "1em",
                    textAlign: "center",
                    accentColor: "rgb(59,111,68)",
                  }}
                  type="checkbox"
                />
                <span>Receive communications from us on messages</span>
              </p>

              {/* <Button
                            style={{
                                "backgroundColor":"rgb(59,111,68)",
                                "color":"rgb(255,255,255)",
                                "borderRadius":"0px",
                                "width":"100%"
                            }}
                            >
                                SEND OTP
                            </Button> */}

              <Button
                type="submit"
                style={{
                  backgroundColor: "rgb(59,111,68)",
                  color: "rgb(255,255,255)",
                  borderRadius: "0px",
                  width: "100%",
                }}
              >
                SUBMIT
              </Button>
            </form>
          </OffcanvasBody>
          {/* {console.log(isSmall)} */}
          <div
            style={{
              margin: "4%",
              textAlign: "center",
              color: "rgb(48,48,48)",
            }}
          >
            To know more check out{" "}
            <b>
              <a
                style={{
                  color: "rgb(48,48,48)",
                }}
                href="https://www.woodlandworldwide.com/refund-policy"
              >
                FAQ’s
              </a>
            </b>
          </div>
        </Offcanvas>
      </React.Fragment>
    );


}

export default Register