import React, { useContext, useReducer, useState } from "react"
import { Col, Row } from "react-bootstrap"
import Woodlandheader from "../../components/Header/Header"
import '../../assets/styles/Myordersstyle.css'
import Getorders from "./getOrders"
import useWindowsizes from "../../components/Windowsize"
import MyAddress from "./getAddress"
import MyProfile from "./getProfile"
import Footer from "../../components/footer/footer"
import { useNavigate } from "react-router-dom"
import { ToastContainer } from "react-toastify"
import UserInfoContext from "../../components/Header/userInfoContext"



const Myorders=()=>{
    const [userDetail,setUserDetail] =useContext(UserInfoContext)

    const [state,setState]=useState({
        'myOrder':true,
        'myProfile':false,
        'Address':false,
        
    })
    const navigate = useNavigate();

    const styles={
    "text-decoration": "underline",
        "color": "green",
    "text-underline-offset": "8px"
    }
    const orderstyles= state.myOrder? {
        "text-decoration": "underline",
        "color": "green",
        "text-underline-offset": "8px"
    }:{}

    const orderStyles= state.myOrder? styles:{}
    const profileStyle=state.myProfile?styles:{}
    const addresStyles=state.Address?styles:{}


    const {isSmall,setSmall}=useWindowsizes();

    return(
        <React.Fragment>
             <header>
                    <Woodlandheader/> 
            </header>
            <body
            className="myorder-content"
            >
              <ToastContainer/>
                    <Row>
                        <Col sm={4}>
                            <div className="myorder-header-name">
                                My Order
                            </div>
                        </Col>
                    </Row>
                    <Row>
                        <Col sm={4}
                        className="myorder-welcome">
                            Welcome to Woodland
                        </Col>
                    </Row>

                    <br/>
                    <Row>
                        <Col>
                            HI! &nbsp;
                            <span>
                             {
                                userDetail!=null?
                                userDetail.firstname
                                :
                                 "GUEST"
                             }
                            </span>
                        </Col>
                    </Row>

                    <br/>
                    {
                        isSmall<900?
                        <React.Fragment>
                            <Row className="myorder-navigate">
                                    <Col  className="myorder-navigate-col"   xs ={3} sm={3}>
                                    <p style={orderStyles}
                                            onClick={()=>{
                                                setState(
                                                        {
                                                            'myOrder':true,
                                                            'myProfile':false,
                                                            'Address':false
                                                        }
                                                
                                                )
                                            }}
                                            > My Order </p>

                                            
                                    </Col>

                                    <Col xs ={3} sm={3}>
                                    <p style={profileStyle}
                                            
                                            onClick={()=>{
                                                setState({
                                                    'myOrder':false,
                                                    'myProfile':true,
                                                    'Address':false
                                                })
                                            }}
                                            >My Profile</p>
                                    </Col>

                                    <Col xs ={3} sm={3}>
                                    <p style={addresStyles}
                                             onClick={()=>{
                                                setState({
                                                    'myOrder':false,
                                                    'myProfile':false,
                                                    'Address':true
                                                })
                                            }}
                                            >Address</p>
                                    </Col>

                                    <Col   xs ={2} sm={3}>
                                        <p
                                          onClick={()=>{
                                                localStorage.clear();
                                                navigate('/home')
                                            }}
                                        >Logout</p>
                                    </Col>
                                    {
                                            console.log("my state",state)
                                    }

                                    <Col  xs ={12} sm={12}>
                        
                                        {
                                          
                                           state.myProfile?
                                           <MyProfile/>
                                           :
                                           state.Address?
                                           <MyAddress/>
                                           :
                                          <Getorders/>

                                        } 
       
                                    </Col>
                            </Row>
                        </React.Fragment>
                        :
                        <React.Fragment>
                        <Row className="myorder-navigate">
                                    <Col lg ={2} sm={3}>
                                            <p style={orderStyles}
                                            onClick={()=>{
                                                setState({
                                                    'myOrder':true,
                                                    'myProfile':false,
                                                    'Address':false,
                                                })
                                            }}
                                            > My Order </p>

                                            <p style={profileStyle}
                                            
                                            onClick={()=>{
                                                setState({
                                                    'myOrder':false,
                                                    'myProfile':true,
                                                    'Address':false,
                                                })
                                            }}
                                            >My Profile</p>

                                            <p style={addresStyles}
                                             onClick={()=>{
                                                setState({
                                                    'myOrder':false,
                                                    'myProfile':false,
                                                    'Address':true,
                                                })
                                            }}
                                            >Address</p>

                                            <p
                                            onClick={()=>{
                                                localStorage.clear();
                                                setUserDetail({
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
                                            }}
                                            >Logout</p>
                                    </Col>

                                    
                                    <Col  lg ={10} >
                                        {  
                                          state.myProfile?
                                          <Row>
                                            <Col sm={12} lg={6}>
                                                <MyProfile/>
                                            </Col>
                                          </Row>
                                         
                                          :
                                          state.Address?
                                          <MyAddress/>
                                         
                                          :
                                         <Getorders/>
                                        }
                                        {
                                            console.log("my state",state)
                                        }
                                    </Col>
                            </Row>
                        </React.Fragment>
                    }
                    

                   
            </body>
            <footer>
                <Footer/>
            </footer>
            
        </React.Fragment>
    )
}

export default Myorders