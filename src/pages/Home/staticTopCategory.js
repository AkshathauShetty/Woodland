import React from "react"
import { Col, Container, Image, Row } from "react-bootstrap"
import casuals from "../../assets/images/categories/casuals.jpg"
import menap from "../../assets/images/categories/mens_apparel.jpg"
import woapp from "../../assets/images/categories/womens_apparel.jpg"
import {useNavigate} from 'react-router-dom'

const StaticTopCategory=()=>{

    const navigate = useNavigate()
  
        
    const HandleScatClick=(subCategory,subCategoryType,gender)=>{
        // browse bbased on category men/women
        navigate("/browseProducts",
        {
          state:{
                search:subCategory,
                url:`getFilteredProducts/${subCategory}/${subCategoryType}/${gender}`
          }
        } )
        window.scrollTo(0,0)

    }

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
             <Container style={{"padding":"1%",textAlign:"center"}}>
                <Row>
                    <Col  className="align-images"  xs={12}  sm={4} lg={4}>
                    <Image className="img img-fluid" src="https://www.woodlandworldwide.com/_next/image?url=https%3A%2F%2Fassets.woodlandworldwide.app%2Fwoodland-images%2FImages%2Fh_cat_11.png&w=1920&q=75 width='500px' " rounded />
                    <div className="button-on-image">
                        <p className="cat-name">Bestsellers</p>
                        <button  className="cat-buttn"> SHOP NOW </button>
                    </div>
                    </Col>
       
                    <Col  className="align-images" sm={4}  xs={12}  lg={4}>
                    <Image className="img img-fluid" src="https://www.woodlandworldwide.com/_next/image?url=https%3A%2F%2Fassets.woodlandworldwide.app%2Fwoodland-images%2FImages%2Fh_cat_41.png&w=1920&q=75 width='500px' " rounded />
                    <div className="button-on-image">
                        <p className="cat-name">Sneakers</p>
                        <button 
                        onClick={()=>{
                            HandleCatClick('Sneakers','Men') 
                        }}
                         className="cat-buttn"> SHOP NOW </button>
                    </div>
                    </Col>
                 
                    <Col className="mt-xs-4 align-images" xs={12} sm={4}  lg={4}>
                    <Image  className="img img-fluid" src="https://www.woodlandworldwide.com/_next/image?url=https%3A%2F%2Fassets.woodlandworldwide.app%2Fwoodland-images%2FImages%2Fh_cat_31.png&w=828&q=75 width='500px' " rounded />
                    <div
                     className="button-on-image">
                        <p className="cat-name">Boots</p>
                        <button 
                          onClick={()=>{
                            HandleCatClick('Boots','Men') 
                        }}
                        className="cat-buttn"> SHOP NOW </button>
                    </div>
                    </Col>
                </Row>



                <Row>
                    <Col  className="mt-xs-4 align-images"  xs={12}  sm={4} lg={4}>
                    <Image className="img img-fluid" src={casuals} width='500px'  rounded />
                    <div className="button-on-image">
                        <p className="cat-name">Casuals & Canvas</p>
                        <button 
                         onClick={()=>{
                            HandleCatClick('Casuals','Men') 
                        }}
                         className="cat-buttn"> SHOP NOW </button>
                    </div>
                    </Col>
       
                    <Col  className="mt-xs-4 align-images" sm={4}  xs={12}  lg={4}>
                    <Image className="img img-fluid" src={menap}  rounded />
                    <div className="button-on-image">
                        <p className="cat-name">Men's Apparel</p>
                        <button
                         onClick={()=>{
                            HandleCatClick('Jackets','Men') 
                        }}
                          className="cat-buttn"> SHOP NOW </button>
                    </div>
                    </Col>
                 
                    <Col className="mt-xs-4 align-images" xs={12} sm={4}  lg={4}>
                    <Image  className="img img-fluid" src={woapp} width='500px'  rounded />
                    <div className="button-on-image">
                        <p className="cat-name">Women's Apparel</p>
                        <button
                         onClick={()=>{
                            HandleCatClick('Jackets','Women') 
                        }}
                         className="cat-buttn"> SHOP NOW </button>
                    </div>
                    </Col>
                </Row>
            </Container>
            <div style={{
                "marginBottom":"5%",
                "overflow":"hidden"
                }}>
                <Image src="https://www.woodlandworldwide.com/_next/image?url=https%3A%2F%2Fassets.woodlandworldwide.app%2Fwoodland-images%2Flanding_page%2Fmid_end_banner%2Fmid_banner_updated.png&w=1920&q=75" width="100%" height="2%" />
                <div className="button-on-images">
                  <button 
                  onClick={()=>{
                      HandleScatClick('Casuals','Lace up','Men');
                  }}
                  className="cat-buttns"> SHOP NOW </button>
                </div>
            </div>
            
        </React.Fragment>
    )
}

export default StaticTopCategory