import  wp from "../../assets/images/categories/jeans.jpg"
import mp from "../../assets/images/categories/sweaters.jpg"
import { Button, Card, Col, Image, Row } from "react-bootstrap"
import React from "react"
import '../../assets/styles/Homestyle.css'
import s1 from "../../assets/images/categories/menap.jpg"
import s2 from "../../assets/images/categories/womenapp.jpg"
import s3 from "../../assets/images/categories/jackets.jpg"
import s4 from "../../assets/images/categories/shirts.jpg"
import { useNavigate } from "react-router-dom"





const StaticBottomCategory=()=>{

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
                    <Col  className="mt-xs-4 align-images"  xs={12}  sm={6} lg={6}>
                    <Image className="img img-fluid" src={s1} width='100%'  rounded />
                    <div className="button-on-imagest">
                        <p className="cat-namest">Shop Men's Apparel</p>
                        <button
                        onClick={()=>{
                            HandleCatClick('T-shirts','Men')
                            }}
                          className="cat-buttnst"> SHOP NOW </button>
                    </div>
                    </Col>
       
                    <Col  className="mt-xs-4 align-images" sm={6}  xs={12}  lg={6}>
                    <Image className="img img-fluid" src={s2}  width='100%'   rounded />
                    <div className="button-on-imagest">
                        <p className="cat-namest">Shop Women's Apparel</p>
                        <button
                        onClick={()=>{
                            HandleCatClick('T-shirts','Women')
                        }}
                          className="cat-buttnst"> SHOP NOW </button>
                    </div>
                    </Col>

            </Row>

                <Row>
                    <Col  className="mt-xs-4 align-images"  xs={12}  sm={6} md={4} lg={3}>
                    <Image className="img " src={s3} width='250' height="250" rounded />
                    <div className="stats-img">
                        <p className="heads-stats">Jackets</p>
                        <a 
                        onClick={()=>{
                            HandleCatClick('Jackets','Men')
                        }}
                        className="link-stats" >Shop Men </a> |  
                        <a
                        onClick={()=>{
                            HandleCatClick('Jackets','Women')
                        }}
                         className="link-stats">Shop Women </a>
                    </div>
                    </Col>
       
                    <Col  className="mt-xs-4 align-images" sm={6}  xs={12}  md={4} lg={3}>
                    <Image className="img" src={s4}  width='250' height="250px"  rounded />
                    <div className="stats-img">
                        <p className="heads-stats">Shirts</p>
                        <p>  <a
                        onClick={()=>{
                            HandleCatClick('Shirts','Men')
                        }}
                         className="link-stats">Shop  Men </a>| 
                        <a 
                          onClick={()=>{
                            HandleCatClick('Shirts','Women')
                        }}
                        className="link-stats">Shop Women </a></p>
                    </div>
                    </Col>

                    <Col  className="mt-xs-4 align-images"  xs={12}  sm={6}  md={4} lg={3}>
                    <Image className="img " src={mp} width='250' height="250" rounded />
                    <div className="stats-img">
                        <p className="heads-stats">Sweaters</p>
                        <p>  <a
                        onClick={()=>{
                            HandleCatClick('Sweatshirts & Hoodies','Men')
                        }}
                         className="link-stats">Shop Men </a> |  
                         <a 
                           onClick={()=>{
                            HandleCatClick('Sweatshirts & Hoodies','Women')
                        }}
                        className="link-stats" >Shop Women </a></p>
                    </div>
                    </Col>
       
                    <Col  className="mt-xs-4 align-images" sm={6}  xs={12}  md={4} lg={3}>
                    <Image className="img  " src={wp}  width='250' height="250"  rounded />
                    <div className="stats-img">
                        <p 
                        className="heads-stats">Jeans</p>
                        <p>  <a 
                        onClick={()=>{
                            HandleCatClick('Bottoms','Men')
                        }}
                        className="link-stats" >Shop  Men </a>  |
                        <a 
                        onClick={()=>{
                            HandleCatClick('Bottoms','Women')
                        }}
                        className="link-stats">Shop Women </a></p>
                    </div>
                    </Col>

                </Row>

                <div style={{
                    "marginBottom":"1%",
                    "overflow":"hidden"
                    }}>
                <Image src="https://www.woodlandworldwide.com/_next/image?url=https%3A%2F%2Fassets.woodlandworldwide.app%2Fwoodland-images%2Flanding_page%2Fmid_end_banner%2Fend_banner.png&w=1920&q=75" width="100%" height="2%" />
                <div className="button-on-images">
                    <button 
                    onClick={()=>{
                    HandleCatClick('Casuals','Men')
                    }}
                    className="cat-buttns"> SHOP NOW </button>
                </div>
                </div>
        </React.Fragment>
    )
}


export default StaticBottomCategory
