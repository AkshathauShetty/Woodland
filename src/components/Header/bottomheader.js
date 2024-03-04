import React, { useContext, useRef, useState } from "react";
import { Button, Container, Form, Nav, NavDropdown, Navbar, Offcanvas } from "react-bootstrap";
import useWindowsizes from "../Windowsize";
import { MypageContext } from "../../pages/Home/HomePage";
import { useNavigate } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import CategoryHolder from "../Header/categoryHolder";
import '../../assets/styles/bottomHeader.css'
import LabsModal from "../../pages/Home/LabsModal";

const Bottomheader=()=>{

    const [serchKey,setSearchkey] = useState();
    const [show, setShow] = useState(false);
    const [target, setTarget] = useState(null);
    const [category,setCategory]=useState('Men')
    const targetRef = useRef(null);
    const navigate = useNavigate();
    const [type,setType]=useState(null);
    const [showModal, setShowModal] = useState(false)

    const handleModal=(value)=>{
      setShowModal(value)
    }
    
    const navigateNow=(url)=>{
      navigate(url)
    }

    const setHover=(e,val)=>{
     // console.log(e.target,val)
      setType(val);
      setShow(true)
      
    }

    const setLeave=(val)=>{
      setShow(false)
    }

    const size = useWindowsizes();

        return (
            <div className="div_container_header" ref={targetRef}>

              {['lg'].map((expand) => (
                <Navbar key={expand} expand={expand} className="bg-body-tertiary mb-3">
                  <Container className="container_btm_header" fluid>

                        <Navbar.Brand  className="navbar_bottomheader"
                        style={{cursor:"pointer"}}
                        onClick={()=>navigateNow("/")}>
                        <img src="https://www.woodlandworldwide.com/images/h-logo.png" width="100rem"/>
                        </Navbar.Brand>

                        { size.isSmall<=991 ? 
                          <Form onSubmit={(e)=>{
                            e.preventDefault();
                            navigate("/browseProducts",
                            {
                                                state:{url:
                                                `brosweProduct/${serchKey}`
                                                }
                                              }
                                         )
                           // console.log(e)
                          }} 
                          className="d-flex search_area" style={{"display":"inline","border":"1px solid black"}}>
                                <Form.Control
                                onChange={(e)=>{
                                  e.preventDefault();
                               //   console.log(e)
                                  setSearchkey(e.target.value)
                                }}
                                    type="search"
                                    placeholder="Search"
                                    className="me-2 form_field"
                                    aria-label="Search"
                                />
                                <Button 
                                onClick={()=>{
                                 //setCurrentPage("products")
                               //  console.log(currentPage,serchKey)
                                 navigate("/browseProducts",
                                            {
                                                state:{url:
                                                `brosweProduct/${serchKey}`
                                                }
                                              }
                                         )
                                }
                                }
                                className="search_button" variant="outline-success">
                                <svg xmlns="http://www.w3.org/2000/svg" height="16" width="16" viewBox="0 0 512 512">
                                <path fill="#4b4b4b" d="M416 208c0 45.9-14.9 88.3-40 122.7L502.6 457.4c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L330.7 376c-34.4 25.2-76.8 40-122.7 40C93.1 416 0 322.9 0 208S93.1 0 208 0S416 93.1 416 208zM208 352a144 144 0 1 0 0-288 144 144 0 1 0 0 288z"/></svg>
                                </Button>
                          </Form>
                        : ''
                        }

                       
                    
                        <Navbar.Toggle aria-controls={`offcanvasNavbar-expand-${expand}`} />
                        <Navbar.Offcanvas className="btmheader_categories_nav"
                        id={`offcanvasNavbar-expand-${expand}`}
                        aria-labelledby={`offcanvasNavbarLabel-expand-${expand}`}
                        placement="start"
                        >

                        <Offcanvas.Header closeButton>
                            <Offcanvas.Title id={`offcanvasNavbarLabel-expand-${expand}`}>
                            <img src="https://www.woodlandworldwide.com/images/h-logo.png" width="100rem"/>
                    
                            </Offcanvas.Title>
                        </Offcanvas.Header>


                        <Offcanvas.Body className="nav_categories">
                            <Nav className="justify-content-center flex-grow-1 pe-3" id="nav-sidebar">
                                <Nav.Link
                                onMouseOver={(e)=>{
                                //  console.log("mouse entered")
                                  setHover(e,'Men')
                                }}
                                onMouseLeave={(e)=>{
                                //  console.log('mouse left')
                                  setLeave(e,'Men')
                                }}
                                className="nav-sidebars" >
                                  Men
                                </Nav.Link><hr></hr>
                                <Nav.Link 
                                 onMouseOver={(e)=>{
                               //   console.log("mouse entered")
                                  setHover(e,'Women')
                                }}
                                onMouseLeave={(e)=>{
                                //  console.log('mouse left')
                                  setLeave(e,'Women')
                                }}
                                 className="nav-sidebars" href="">Women</Nav.Link><hr></hr>
                                <Nav.Link  className="nav-sidebars" href="">Bags & Gear</Nav.Link><hr></hr>
                                <Nav.Link  className="nav-sidebars" href="">About Us</Nav.Link><hr></hr>
                                <Nav.Link  
                                className="nav-sidebars" 
                                onClick={
                                  ()=>{
                                    handleModal(true)
                                  }
                                }>
                                Labs
                                </Nav.Link><hr></hr>
                                <Nav.Link  className="nav-sidebars"  href="">Sale</Nav.Link><hr></hr>
                            </Nav>                    
                        </Offcanvas.Body>
                        </Navbar.Offcanvas>

                        { size.isSmall>991 ? 
                          <Form 
                          onSubmit={(e)=>{
                            e.preventDefault();
                            navigate("/browseProducts",
                            {
                                                state:{url:
                                                `brosweProduct/${serchKey}`
                                                }
                                              }
                                         )
                          }}
                          className="d-flex search_area" style={{"display":"inline"}}>
                                <Form.Control
                                     onChange={(e)=>{
                                      e.preventDefault();
                                   //   console.log(e)
                                      setSearchkey(e.target.value)
                                    }}
                                    type="search"
                                    placeholder="Search"
                                    className="me-2 form_field"
                                    aria-label="Search"
                                />

                              <Button 
                                onClick={()=>{
                                 //setCurrentPage("products")
                               //  console.log(currentPage,serchKey)
                                 navigate("/browseProducts",
                                              {
                                                state:{url:
                                                `brosweProduct/${serchKey}`
                                                }
                                              }
                                         )
                                }
                                }
                                className="search_button" variant="outline-success">
                                <svg xmlns="http://www.w3.org/2000/svg" height="16" width="16" viewBox="0 0 512 512">
                                <path fill="#4b4b4b" d="M416 208c0 45.9-14.9 88.3-40 122.7L502.6 457.4c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L330.7 376c-34.4 25.2-76.8 40-122.7 40C93.1 416 0 322.9 0 208S93.1 0 208 0S416 93.1 416 208zM208 352a144 144 0 1 0 0-288 144 144 0 1 0 0 288z"/></svg>
                                </Button>  
                          </Form>
                        : ''
                        }
                  </Container>
                </Navbar>
              ))}
              <CategoryHolder
                category={setCategory}
                show={show}
                setShow={setShow}
                setTrue={setHover}
                target={targetRef}
                type={type}
                // ref={refs}
              />

              <LabsModal
                show={showModal}
                handleModal={handleModal}
                onHide={()=>handleModal(false)}
              />
            </div>
          );
    

}

export default Bottomheader