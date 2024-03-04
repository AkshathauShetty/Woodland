import React, { Fragment, useContext, useEffect, useState } from "react"
import '../../assets/styles/Homestyle.css'
import { Container, Nav, Navbar } from "react-bootstrap"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import Bottomheader from "./bottomheader"
import Addtocart from "../../pages/Cart/addTocart"
import Register from "../../pages/Home/register"
import { useNavigate } from "react-router-dom"
import { ToastContainer, toast } from "react-toastify"
import 'react-toastify/dist/ReactToastify.css'
import useLoggedin from "../methods/useLoggedin"
import UserInfoContext from "./userInfoContext"


const Woodlandheader =()=>{
    const [modalShow, setModalShow] = useState(false);
    const [registerShow, setRegisterShow] = useState(false);
    const [isLoggedin,setIsLogged] =useState(localStorage.getItem('isLoggedin'))
    const isLoggedins =  localStorage.getItem(localStorage.getItem('phone'))

    const [userDetail,setUserDetail] =useContext(UserInfoContext)

    const navigate=useNavigate();


    useEffect(()=>{
       // console.log("calling the header ue-->", isLoggedin,registerShow)
        if(localStorage.getItem('isLoggedin')){
            setIsLogged(true)
        }
       
    },[isLoggedin,registerShow])

    useEffect(()=>{

        if(isLoggedin){
            setUserDetail({
                "firstname":localStorage.getItem('name'),
                "token":localStorage.getItem(localStorage.getItem('phone')),
                "isLogged":localStorage.getItem('isLoggedin')
            })
        }
    },[isLoggedin])


    /**
     * Called when cart is clicked
     * function : if the user is logged in display the user cart 
     * else: display an error  message.
     */
    const showCart=()=>{
        if(isLoggedin){
            setModalShow(true)
        }
        else{

            toast.error(' Please Login to continue!', {
                position: "top-right",
                autoClose: 5000,
                hideProgressBar: true,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "dark",
                });
        
          
        }

    }

    return(
        <React.Fragment>

      
            <header>
              
                <div className="common-header" id="top-header">
                    Woodland does not call the customer for asking OTP. Please do not share any OTP with anyone on call to avoid fraudulent transactions.
                </div>

                <Navbar  id="inner-header">      
                    <Nav className="justify-content-end nav-item" id="inner-header-text">
                        <Nav.Link 
                        onClick={()=>{
                            showCart()
                        }}
                        className="nav-item"
                        >
                        <FontAwesomeIcon icon="fa-solid fa-cart-shopping" style={{color: "#fafcff",}} />
                            <svg xmlns="http://www.w3.org/2000/svg" height="16" width="18" viewBox="0 0 576 512">
                            <path fill="#fafcff" d="M0 24C0 10.7 10.7 0 24 0H69.5c22 0 41.5 12.8 50.6 32h411c26.3 0 45.5 25 38.6 50.4l-41 152.3c-8.5 31.4-37 53.3-69.5 53.3H170.7l5.4 28.5c2.2 11.3 12.1 19.5 23.6 19.5H488c13.3 0 24 10.7 24 24s-10.7 24-24 24H199.7c-34.6 0-64.3-24.6-70.7-58.5L77.4 54.5c-.7-3.8-4-6.5-7.9-6.5H24C10.7 48 0 37.3 0 24zM128 464a48 48 0 1 1 96 0 48 48 0 1 1 -96 0zm336-48a48 48 0 1 1 0 96 48 48 0 1 1 0-96z"/>
                            </svg>
                        &nbsp;Cart
                        </Nav.Link>

                        {
                            isLoggedin?
                            <Nav.Link 
                             onClick={()=>{
                                navigate('/myOrders')
                            }}
                             className="nav-item" href="#features">
                        |  &nbsp;Order Status
                            </Nav.Link>:''
                        }

                        <Nav.Link className="nav-item" href="#features">
                        |  &nbsp;Find Store
                        </Nav.Link>

                        {
                            isLoggedin?
                            <Nav.Link 
                            onClick={()=>{
                                navigate('/myOrders')
                            }}
                            className="nav-item" href="#">
                            | &nbsp;
                            <span>
                            {
                                userDetail.firstname==null ||   userDetail.firstname=="null"?
                                localStorage.getItem('name')!=null  &&   localStorage.getItem('name')!='null'?
                                <>
                                {
                                    localStorage.getItem('name')
                                }
                                {
                                    setUserDetail({
                                    "firstname": localStorage.getItem('name')
                                    })
                                }
                                </>
                                :
                                <>
                                'Guest'
                               {localStorage.setItem('name',"Guest")}
                                </>
                              
                            :
                            userDetail.firstname
                            }
                            &nbsp;</span> 
                            <svg xmlns="http://www.w3.org/2000/svg" height="14" width="12.25" viewBox="0 0 445 512">
                            <path fill="#ffffff" d="M224 256A128 128 0 1 0 224 0a128 128 0 1 0 0 256zm-45.7 48C79.8 304 0 383.8 0 482.3C0 498.7 13.3 512 29.7 512H418.3c16.4 0 29.7-13.3 29.7-29.7C448 383.8 368.2 304 269.7 304H178.3z"/></svg>
                            </Nav.Link>
                            :
                            <Nav.Link 
                            onClick={()=>{
                                setRegisterShow(true)
                                    }}
                            className="nav-item" >
                            |&nbsp;Sign in&nbsp;
                            <svg xmlns="http://www.w3.org/2000/svg" height="12" width="12.25" viewBox="0 0 448 512">
                            <path fill="#ffffff" d="M224 256A128 128 0 1 0 224 0a128 128 0 1 0 0 256zm-45.7 48C79.8 304 0 383.8 0 482.3C0 498.7 13.3 512 29.7 512H418.3c16.4 0 29.7-13.3 29.7-29.7C448 383.8 368.2 304 269.7 304H178.3z"/></svg>
                            </Nav.Link>
                           
                          
                        }
                       
                       
                    </Nav>     
                </Navbar>
                <ToastContainer/>
                <Bottomheader/>
                
                <Addtocart
                    show={modalShow}
                    onHide={()=>{
                        setModalShow(false)
                    }}
                />
                <Register
                    // loggedIn={[isLoggedin,setIsLogged]}
                    show={registerShow}
                    onHide={()=>{
                        setRegisterShow(false)
                    }}
                />
                
            </header>
        </React.Fragment>
    )


}



export default Woodlandheader