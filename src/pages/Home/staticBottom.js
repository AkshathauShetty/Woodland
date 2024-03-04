

import { Button, Card, Col, Image, Row } from "react-bootstrap"
import React from "react"
import '../../assets/styles/Homestyle.css'


import bags from "../../assets/images/categories/bags.jpg"
import inrwear from "../../assets/images/categories/innerwear.jpg"
import belts from "../../assets/images/categories/belts.jpg"

import pf1 from "../../assets/images/categories/pf1.jpg"
import pf2 from "../../assets/images/categories/pf2.jpg"
import pf3 from "../../assets/images/categories/pf3.jpg"
import pf4 from "../../assets/images/categories/pf4.jpg"

import f1 from "../../assets/images/categories/woodlReasearch.jpg"
import f2 from "../../assets/images/categories/planet.jpg"
import { useNavigate } from "react-router-dom"

const StaticBottom=()=>{
    const navigate = useNavigate()
  


    const HandleCatClick=(subCategory,gender)=>{  
      navigate("/browseProducts",
      {
        state:{
          search:subCategory,
          url:`getFilteredProducts/${subCategory}/ /${gender}`
        }
      } )  
        window.scrollTo(0,0); 
    }

    return(
        <React.Fragment>
               <Row>
                    <Col style={{"margin":"0px","padding":"0px"}} className="mt-xs-4 align-images"  xs={12}  sm={4} lg={4}>
                    <Image className="img img-fluid" src={bags} width='100%'  rounded />
                    <div className="button-on-image">
                        <p className="cat-namest">Bags</p>
                        <button 
                           onClick={()=>{
                            HandleCatClick('Backpacks','Men')
                            }}
                         className="cat-buttn"> SHOP NOW </button>
                    </div>
                    </Col>
       
                    <Col  style={{"margin":"0px","padding":"0px"}} className="mt-xs-4 align-images" sm={4}  xs={12}  lg={4}>
                    <Image className="img img-fluid" src={inrwear}  width='100%'    />
                    <div className="button-on-image">
                        <p className="cat-namest">Innerwear</p>
                        <button
                         onClick={()=>{
                            HandleCatClick('Bottoms','Men')
                            }}
                        className="cat-buttn"> SHOP NOW </button>

                    </div>
                    </Col>

                    <Col  style={{"margin":"0px","padding":"0px"}} className="mt-xs-4  align-images" sm={4}  xs={12}  lg={4}>
                    <Image className="img img-fluid" src={belts}  width='100%'   />
                    <div className="button-on-image">
                        <p className="cat-namest">Belts & More</p>
                        <button
                        onClick={()=>{
                            HandleCatClick('Belts','Men')
                            }}
                          className="cat-buttn"> SHOP NOW </button>
                    </div>
                    </Col>

            </Row>

            <div style={{"margin":"2%"}}>
                <h3 style={{"fontFamily":"sans-serif"}}>AS WORN BY WOODLAND FAM</h3>
                <h6>Click to shop & mention us on instagram @woodlandworldwide to be featured</h6>
            </div>


            <Row>
                    <Col  className="mt-xs-4 align-images"   xs={3}>
                    <Image className="img img-fluid" src={pf1} width='100%'  rounded />

                    </Col>
       
                    <Col  className="mt-xs-4 align-images"  xs={3}>
                    <Image className="img img-fluid" src={pf2} width='100%'  rounded />

                    </Col>

                    <Col  className="mt-xs-4 align-images"  xs={3}>
                    <Image className="img img-fluid" src={pf3} width='100%'  rounded />

                    </Col>

                    <Col  className="mt-xs-4 align-images"  xs={3}>
                    <Image className="img img-fluid" src={pf4} width='100%'  rounded />

                    </Col>

            </Row>

            <Row>
                    <Col  className="mt-xs-4 align-images"   xs={12}   lg={6} >
                    <Image className="img img-fluid" src={f1} width='100%'  rounded />
                    <div className="button-on-image">
                        <h1 className="cat-namest">Pro Planet</h1>
                        <p className="cat-namest">
                        Proplanet is a community that is committed to make our planet a better place to live in. Our ultimate goal is to inspire the next generation and increase their participation in the conservation of nature.
                        </p>
                        <button  className="cat-buttnst"> Read More </button>
                    </div>
                    </Col>
       
                    <Col  className="mt-xs-4 align-images"  xs={12}  lg={6}>
                    <Image className="img img-fluid" src={f2}   rounded />
                    <div className="button-on-image">
                        <h1 className="cat-namest">Woodland Reasearch Lab</h1>
                        <p className="cat-namest"s>
                        Woodlands expertise combines activity focused designs and technologically advanced materials available, created in the most innovative and effective manner possible.
                        </p>
                        <button  className="cat-buttnst"> Read More </button>
                    </div>
                    </Col>
            </Row>
        </React.Fragment>
    )
}

export default StaticBottom