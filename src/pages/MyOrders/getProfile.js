import React, { useContext, useEffect, useReducer, useRef, useState } from "react"
import { Button, Col, Row } from "react-bootstrap"
import '../../assets/styles/MyProfile.css'
import useWindowsizes from "../../components/Windowsize";

import useAuthGetrequests from "../../components/customRequests/getreqAuth";
import { request, token } from "../../components/variables/variables";
import { errorMessage, successMessage, validateInput }  from "../../components/methods/computations";
import { ACTION } from "../../components/variables/variables";
import UserInfoContext from "../../components/Header/userInfoContext";

const MyProfile=()=>{

  const [userDetail,setUserDetail] =useContext(UserInfoContext)

    const {isSmall,setSmall}=useWindowsizes();
    const [inputs, setinputDisable]=useState(true)
    // const token =  localStorage.getItem(localStorage.getItem('phone'))
    const [profile,setProfile]=useAuthGetrequests(`getProfile`)
    const [userData,setUserData]=useState({
        "firstname":null,
        "lastname":null,
        "email":null,
        "phone":null,
         "dob" :'',
        "gender":null,
        
    })
    const [chekedField,setCheckedField]=useState({
      male:false,
      female:false
    })

    const [maleGenderBox,setMaleGenderBox] = useState(false)
    const [femaleGenderBox,setFemaleGenderBox] = useState(false)


    const firstnameInput = useRef(null)
    const lastnameInput = useRef(null)
    const emailnameInput = useRef(null)
    const dobInput = useRef(null)
    const genderInput = useRef(null)

    const userdatas =useRef({
        "firstname":null,
        "lastname":null,
        "email":null,
         "dob" :'2002/02/02',
        "gender":'female',      
    })

    const setGenderCheckbox=(gender)=>{
      if(gender=="female"){
        setFemaleGenderBox(true)
      }
      else if(gender=="male"){
        setMaleGenderBox(true)
      }
    }

    useEffect(()=>{
        // console.log('rendered aagin')
        if(profile!=null){
            firstnameInput.current.value=profile.firstname          
            lastnameInput.current.value=profile.lastname
            emailnameInput.current.value=profile.email
            if(profile.dob!=null){            
            dobInput.current.value=profile.dob.slice(0,10)
            }
            genderInput.current.value=profile.gender
            setGenderCheckbox(profile.gender)
            userdatas.current=profile
             setUserData(profile)
        }

    },[profile])
   
    const updateInputs=(e,toUpdate)=>{
        switch(toUpdate){
            case ACTION.FIRST_NAME:console.log(firstnameInput); firstnameInput.current.value=e.target.value;break;
            case ACTION.LAST_NAME:lastnameInput.current.value=e.target.value;break; 
            case ACTION.EMAIL:emailnameInput.current.value=e.target.value;break; 
            case ACTION.GENDER: genderInput.current.value=e.target.value; break;
            case ACTION.DOB:dobInput.current.value=e.target.value;break;
        }

    }

    const disableInputs=(value)=>{
        setinputDisable(value)
    }

    const updateChange=()=>{
        disableInputs(true);
        console.log('inside updatechages',genderInput,dobInput)
        let tokens = userDetail.token!=null? userDetail.token : localStorage.getItem(localStorage.getItem('phone'))
       
        request
            .post('/update', 
                {
                    "firstname":firstnameInput.current.value,
                    "lastname":lastnameInput.current.value,
                    "email":emailnameInput.current.value,     
                    "phone":localStorage.getItem('phone'),    
                    "dob" :dobInput.current.value,
                    "gender":genderInput.current.value
                },
                {
                    headers:{
                        "Content-Type":"application/json",
                        "Access-Control-Allow-Header":"Content-Type",
                        "Access-Control-Allow-Origin": "*",
                        "Authorization" : `Bearer ${tokens}`

                    }
    
                })
            .then((response)=>{
                successMessage('Profile updated Successfully!')
                // setUserDetail(response.data.firstname);
                setUserDetail({
                  "firstname":response.data.firstname
                })
                localStorage.setItem('name',response.data.firstname)
                console.log("response data on profile updates:",response.data.firstname,userDetail)
                // setPost(response.data.firstname);
                // console.log("userpost: ",userpost)
            }
            )
            .catch((error)=>{
                console.log('profile error',token)
                alert(error)
            });
    }

    const validateInputFields=()=>{
        if(!validateInput("name",firstnameInput.current.value)){
            errorMessage('Invalid name')
        }

        else  if(!validateInput("email",emailnameInput.current.value)){
            errorMessage('Invalid email')
        }
        else{
            updateChange();
        }
    }

    return (
      <React.Fragment>

        <Row>
          <Col xs={6}>
            <p>Contact Details </p>
          </Col>
          <Col
            style={{
              paddingLeft: "2%",
              textAlign: "end",
            }}
            xs={6}
          >
            <p
              onClick={() => {
                disableInputs(false);
              }}
              style={{
                color: "rgb(59,111,68)",
              }}
            >
              Edit{" "}
            </p>
          </Col>
        </Row>
        <br></br>
        {isSmall < 900 ? (
          <Row>
            <Col className="profile_fields" xs={6}>
              <label>First Name</label>
              <br />
              <input
                onChange={(e) => {
                  updateInputs(e, "firstname");
                }}
                disabled={inputs}
                ref={firstnameInput}
                className="profile_input"
              />

              <br />

              <label>Last Name</label>
              <br />
              <input
                onChange={(e) => {
                  updateInputs(e, "lastname");
                }}
                disabled={inputs}
                ref={lastnameInput}
                className="profile_input"
              />
              <br />

              <label>Email ID</label>
              <br />
              <input
                onChange={(e) => {
                  updateInputs(e, "email");
                }}
                disabled={inputs}
                ref={emailnameInput}
                className="profile_input"
              />
              <br />

              <label>Phone No.</label>
              <br />
              <input
                disabled="true"
                value={localStorage.getItem("phone")}
                className="profile_input"
              />
              <br />
            </Col>
          </Row>
        ) : (
          <React.Fragment>
            <Row>
              <Col className="profile_fields" xs={6}>
                <label>First Name</label>
                <br />
                <input
                  onChange={(e) => {
                    updateInputs(e, "firstname");
                  }}
                  disabled={inputs}
                  ref={firstnameInput}
                  className="profile_input"
                />
              </Col>

              <Col className="profile_fields" xs={6}>
                <label>Last Name</label>
                <br />

                <input
                  onChange={(e) => {
                    updateInputs(e, "lastname");
                  }}
                  disabled={inputs}
                  ref={lastnameInput}
                  className="profile_input"
                />
              </Col>
            </Row>
            <br />
            <Row>
              <Col className="profile_fields" xs={6}>
                <label>Email ID</label>
                <br />
                <input
                  onChange={(e) => {
                    updateInputs(e, "email");
                  }}
                  disabled={inputs}
                  ref={emailnameInput}
                  className="profile_input"
                />
              </Col>

              <Col className="profile_fields" xs={6}>
                <label>Phone No.</label>
                <br />
                <input
                  disabled="true"
                  value={localStorage.getItem("phone")}
                  className="profile_input"
                />
              </Col>
            </Row>
          </React.Fragment>
        )}

        <br />
        <Row>
          <Col xs={6}>
            <label>Birthday</label>
            <br />
            <input
              onChange={(e) => {
                console.log("dooooob", e, dobInput);
                updateInputs(e, "dob");
              }}
              disabled={inputs}
              ref={dobInput}
              className="profile_input"
              type="date"
            />
            <br />
          </Col>

          <Col xs={6}>
            <div>
              <label>Gender</label>
              <br />
              <input
                onChange={(e) => {
                  if(!inputs){
                    console.log("gender", e, genderInput);
                    setFemaleGenderBox(false)
                    setMaleGenderBox(true)
                    updateInputs(e, "male", e.target.checked);
                  }              
                }}
                ref={genderInput}
                type="radio"
                id="male"
                name="gender"
                value="male"
                checked={maleGenderBox}
              />
              &nbsp;
              <label for="male">Male</label>&nbsp;
              <input
                onChange={(e) => {
                  if(!inputs){
                    console.log("gender", e, genderInput);
                    setMaleGenderBox(false)
                    setFemaleGenderBox(true)
                    updateInputs(e, "female", e.target.checked);
                  }             
                }}
                ref={genderInput}
                type="radio"
                id="female"
                name="gender"
                value="female"
                checked={femaleGenderBox}
              />{" "}
              &nbsp;
              <label for="female">Female</label>&nbsp;
            </div>
          </Col>
        </Row>
        <br></br>
        {inputs ? (
          ""
        ) : (
          <Row className="justify-content-end">
            <Col xs={3}>
              <Button
                onClick={() => {
                  disableInputs(true);
                }}
                style={{
                  backgroundColor: "rgb(48,48,48)",
                }}
                className="profile_buttons cancel_Button"
              >
                Cancel
              </Button>
            </Col>
            <Col xs={3}>
              <Button
                onClick={() => {
                  validateInputFields();
                }}
                style={{
                  backgroundColor: "rgb(59,111,68)",
                }}
                className="profile_buttons save_Button"
              >
                Save
              </Button>
            </Col>
          </Row>
        )}
      </React.Fragment>
    );
}

export default MyProfile