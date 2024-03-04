import { useReducer, useRef, useState } from "react";
import { Button, Col, Overlay, Row } from "react-bootstrap"
import "../../assets/styles/CategoryHolder.css"
import useGetcategories from "../customRequests/getCategories";
import { useNavigate } from "react-router-dom";

/**
 * Overlay on hover for men and women categories products
 * @param {products info} props 
 * @returns categories overlay
 */


const CategoryHolder=(props)=>{

    const [categories,setCategories] = useGetcategories(props.type);
    const ref = useRef(null);
    const navigate = useNavigate()

    const HandleScatClick=(categoryGivenName,subCategory,subCategoryType)=>{
        // browse bbased on category men/women
        navigate("/browseProducts",
        {
          state:{
                search:subCategory,
                category:categoryGivenName,
                for:props.type,
                url:`getFilteredProducts/${subCategory}/${subCategoryType}/${props.type}`
          }
        } )

    }

    const HandleCatClick=(categoryGivenName,subCategory)=>{  
      navigate("/browseProducts",
      {
        state:{
          search:subCategory,
          category:categoryGivenName,
          for:props.type,
          url:`getFilteredProducts/${subCategory}/ /${props.type}`
        }
      } )   
    }
    

    const subcategories = (categoryGivenName) => {
      return categories != null && categories.length > 0
        ? categories.map((category) => {
            if (
              category.categoryName == categoryGivenName &&
              category.subCategoryDto != null &&
              category.subCategoryDto.length > 0
            ) {
              return category.subCategoryDto.map((subCategory) => {
                return (
                  <Col className="overlay_col_item" lg={12}>
                    <span
                      onClick={() => {
                        HandleCatClick(categoryGivenName,subCategory.subcategoryName);
                      }}
                      className="cat_each_col"
                    >
                      {subCategory.subcategoryName}
                    </span>
                    {subCategory.subcategoryTypesDto != null &&
                    subCategory.subcategoryTypesDto.length > 0
                      ? subCategory.subcategoryTypesDto.map(
                          (subcategoryType) => {
                            return (
                              <Row style={{ paddingLeft: "20px" }}>
                                <Col
                                  className="cat_each_col"
                                  onClick={() => {
                                    HandleScatClick(categoryGivenName,
                                      subCategory.subcategoryName,subcategoryType.subcategoryTypeName
                                    );
                                  }}
                                  style={{ fontWeight: "400" }}
                                  lg={12}
                                >
                                  {subcategoryType.subcategoryTypeName}
                                </Col>
                              </Row>
                            );
                          }
                        )
                      : ""}
                  </Col>
                );
              });
            }
          })
        : "";
    }; 

    return (
      <div 
      onMouseEnter={()=>props.setShow(true)}
      onMouseLeave={()=>props.setShow(false)}
      ref={ref}>
        <Overlay
          show={props.show}
          target={props.target}
          placement="bottom"
          className="overlay_container"
        >
             
                   <Row className="overlay_home_categories">
                    <Col className="overlay_home_col" lg={2} xs={4} style={{textAlign:"left"}}>
                        <Row className="overlay_col_one" style={{textAlign:"start"}}>
                            <Col className="overlay_colone_item" lg={12}>
                              Sales
                            </Col>
                            <Col className="overlay_colone_item" lg={12}>
                                New Arrivals
                            </Col>
                            <Col className="overlay_colone_item" lg={12}>
                                Woodsports
                            </Col>
                            <Col className="overlay_colone_item" lg={12}>
                            Woods
                            </Col>
                            <Col className="overlay_colone_item" lg={12}>
                                StreetWear
                            </Col>
                        </Row>
                    </Col>
                    <Col className="overlay_home_head" lg={2} xs={4}>
                        <Row className="overlay_col_two" style={{textAlign:"start"}}>
                            <Col className="overlay_col_header" lg={12}>
                              Footwear
                            </Col>
                            {
                              subcategories('Footwear')
                            }
                        </Row>
                    </Col>
                    <Col className="overlay_home_head" lg={2} xs={4}>
                        <Row className="overlay_col_two" style={{textAlign:"start"}}>
                            <Col className="overlay_col_header" lg={12}>
                              Clothing Tops
                            </Col>
                            {
                              subcategories('Clothing Tops')
                            }
                            
                        </Row>
                    </Col>

                    <Col className="overlay_home_head" lg={2} xs={4} style={{textAlign:"left"}}>
                        <Row className="overlay_col_two" style={{textAlign:"start"}}>
                            <Col className="overlay_col_header" lg={12}>
                              Clothing Bottoms
                            </Col>
                            {
                              subcategories('Clothng Bottoms')
                            }
                            
                        </Row>
                        <Row className="overlay_col_two" style={{textAlign:"start"}}>
                            <Col className="overlay_col_header" lg={12}>
                              Bags
                            </Col>
                            {
                              subcategories('Bags')
                            }
                            
                        </Row>
                    </Col>
                    <Col className="overlay_home_head" lg={2} xs={4}>
                        <Row className="overlay_col_two" style={{textAlign:"start"}}>
                            <Col className="overlay_col_header" lg={12}>
                              Accessories
                            </Col>
                            {
                              subcategories('Accessories')
                            }
                            
                        </Row>
                        <Row className="overlay_col_two" style={{textAlign:"start"}}>
                            <Col className="overlay_col_header" lg={12}>
                            Wearables
                            </Col>
                            {
                              subcategories('Wearables')
                            }
                            
                        </Row>
                        <Row className="overlay_col_two" style={{textAlign:"start"}}>
                            <Col className="overlay_col_header" lg={12}>
                            Personal Care
                            </Col>
                            {
                              subcategories('Personal Care')
                            }
                            
                        </Row>
                    </Col>
                    </Row>   
        </Overlay>
      </div>
    );
}


export default CategoryHolder