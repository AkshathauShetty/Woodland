import React, { useContext, useEffect, useReducer, useRef, useState } from "react"
import { MypageContext } from "../../pages/Home/HomePage"
import useGetrequests from "../customRequests/getrequest"
import ProductCarousal from "../components/ProductCarousal"
import { Card, Col, Dropdown, DropdownButton, DropdownItem, Row } from "react-bootstrap"
import Woodlandheader from "../Header/Header"
import Footer from "../footer/footer"
import { useLocation } from "react-router-dom"
import '../../assets/styles/browseProducts.css'


const FILTER = {
    BY_SIZE:"size",
    BY_COLOR:"color", 
    BY_PRICE:"price", 
    BY_BRAND:"brand",
    SET_VALUE:"setValue",
    RESET:"reset"
}

const reducer=(state,action)=>{
    switch(action.filters){
        case FILTER.SET_VALUE : return action.value;
        case FILTER.BY_SIZE:return  action.original.filter((item)=>{ return  item.productPicturesdto[0]!=null && item.productPicturesdto[0].productSizes.filter((size)=>size==action.value).length>0 ? item :'' });
        case FILTER.BY_COLOR:return  action.original.filter((item)=>{return item.productPicturesdto!=null && item.productPicturesdto[0]!=null &&item.productPicturesdto[0].hasOwnProperty('productColor')? item.productPicturesdto[0].productColor.includes(action.value):''}) 
        case FILTER.BY_PRICE: return  action.original.filter((item)=>
                            {
                               if(action.value.min==5000){
                                return item.productFinalPrice>action.value.min 
                               }
                               else{
                                    return item.productFinalPrice>action.value.min && item.productFinalPrice<action.value.max
                                }})
        case FILTER.BY_BRAND: return  action.original.filter((item)=>{ return item.productdtoSize==action.value})
        default : return state;

    }
}




const BrowseProducts=(props)=>{

    const {state} = useLocation();
    const [isSmall,setSmall] = useState(window.innerWidth);
    const [products,setProducts] =useGetrequests(state.url);
    const [path,setPath] = useState('')
    const [filter, setFilter] = useReducer(reducer,[]);
    const [selectedFilter,setSelectedFilter] = useState({
      size:"All sizes",
      price:"Select Price Range",
      color:"All color",
      brand:"All Brands"

    })
   const [result,setResult]=useState([]);
  const CategoryQuotes = window.Categories

    useEffect(()=>{

     // console.log("state has been changed."+state.url)
      setProducts(null);
    },[state])

    useEffect(()=>{
        setFilter({
            filters:"setValue",
            value:products
        })

    },[products])

    const pricerange=['1000-2000','2000-3000','3000-4000','4000-5000','5000 and more']
    const color= [
        'blue',
        "white",
        "brown",
        "grey",
        "beige",
        "camel",
        "olive",
        "Khaki",
        "Green",
        "Pink",
        "Yellow",
        "Maroon",
        "Red",
        "Purple",
        "Tan",
        "Camouflage",
        "Orange",
        "Coffee"
    ]

    const sizes =[
        '26','28','30','32','33','34','35','36','37','38','39','40','41','42','43','44','45','46','47','L','M','S','XL','XS','2X'
    ]

    const brands=["Woods", "Woodland", "Woodsports", "ASKATINGMONK"]

    useEffect(()=>{
      // if(result[0]=='brosweProduct'){
      //   setPath("result");
      // }
      // else{
        let url = state.url; 
        let delimiters = "/"; // the delimiters to use
        let result=url.split(new RegExp("[" + delimiters + "]"));
        setResult(result); // split the string by the delimiters
        if(result[0]=='brosweProduct'){
          setPath("result");
       //   console.log('new path:',url,result)
        }
        else{
       //   console.log('new path:',url,result)
          setPath(result)
          if(result[2]==' '){
            setPath(`${result[result.length-1].toUpperCase()}/${result[1].toUpperCase()}`)
       
          }
          else{
            setPath(`${result[result.length-1].toUpperCase()}/${result[1].toUpperCase()}_${result[2].toUpperCase()}`)
          }
         }  
      //}
     
    },[products])

    // const getType=(varible)=>{
    //   return type(variable;
    // }

    return (
      <React.Fragment>
        {
          <React.Fragment>
            <header>
              <Woodlandheader />
            </header>
  
            <div 
            style={{
              paddingLeft:"6%",
              paddingRight:"2%"
            }}
            className="filter-function">
            <img
              className="img-fluid"
              src="https://www.woodlandworldwide.com/_next/image?url=https%3A%2F%2Fassets.woodlandworldwide.app%2Fwoodland-images%2Fbanners%2FMALE_APPAREL_JACKETS.png&w=1920&q=75"
            />
             <p className="route_path">HOME / {path}</p>
             <br/>  
             {
                result.length>0 && path!='result'?
                isSmall>=100?

                  <div className="cat_quote_container">
                  <div className="prod_cat_name">
                  {
                      result.length>0?
                      <span>
                      {result[result.length-1].toUpperCase()}S &nbsp;
                      {
                        result[2]!=null?
                        result[2].trim().length>0?
                        result[2].toUpperCase()
                        :
                        result[1].toUpperCase()
                        :
                        ''
                      }
                      
                      </span>
                      :
                      ''
                    }
                  </div>
                  <div className="prod_cat_desc">
                  {
                      CategoryQuotes.hasOwnProperty(result[1].toUpperCase())?
                      result[2].trim().length>0?
                      CategoryQuotes[result[1].toUpperCase()][result[2].toUpperCase()]
                      :
                      typeof(CategoryQuotes[result[1].toUpperCase()])=="object"?
                      <>
                      {
                       //   console.log("In MID", CategoryQuotes[result[1].toUpperCase()])
                        }
                        {
                          CategoryQuotes[result[1].toUpperCase()][result[1].toUpperCase()]
                        }
                      </>
                  
                      :
                      <>
                        
                        {
                          CategoryQuotes[result[1].toUpperCase()]
                        }
                      </>
                      :
                     ''
                    }
                  </div>
                  </div>
                :
              ''
              :
              ''             
             }
              <p style={{textAlign:"left",fontWeight:"700"}}>FILTER BY</p>
              <Row>
                <Col className="browse_filter_col" xs={12} sm={2} lg={2}>
                  <Dropdown 
                  autoClose="true"
                  align="start"
                  className="d-inline mx-auto" xs={12}>
                    <Dropdown.Toggle
                      className="filter-buttons"
                      id="dropdown-autoclose-true"
                      
                    >
                    {
                      selectedFilter.size
                    }
                    </Dropdown.Toggle>

                    <Dropdown.Menu 
                  
                    className="browse_filter_dropdown">
                      {sizes.map((size) => (
                        <Dropdown.Item
                        
                          href="#"
                          onClick={() => {
                            setFilter({
                              filters: "size",
                              value: size.toString(),
                              original: products,
                            });
                            setSelectedFilter({
                              ...selectedFilter,
                              size:size
                            })
                          }}
                        >
                          {" "}
                          {size}
                        </Dropdown.Item>
                      ))}
                    </Dropdown.Menu>
                  </Dropdown>
                </Col>

                <Col className="browse_filter_col" xs={12} sm={4} lg={2}>
                  <Dropdown  align="start" className="d-inline mx-auto">
                    <Dropdown.Toggle id="dropdown-autoclose-true">
                      {selectedFilter.price}
                    </Dropdown.Toggle>

                    <Dropdown.Menu>
                      {pricerange.map((price) => (
                        <Dropdown.Item
                        style={{
                          color: "#3b6f44"
                        }}
                          onClick={() => {
                          //  console.log(filter, "this if filter", price);
                            setFilter({
                              filters: "price",
                              value: {
                                min: parseInt(price.slice(0, 4)),
                                max: parseInt(price.slice(5, 9)),
                              },
                              original: products,
                            });
                            setSelectedFilter({
                              ...selectedFilter,
                              price:price
                            })
                          }}
                          href="#"
                        >
                          {" "}
                          {price}
                        </Dropdown.Item>
                      ))}
                    </Dropdown.Menu>
                  </Dropdown>
                </Col>

                <Col className="browse_filter_col" xs={12} sm={3} lg={2}>
                  <Dropdown  align="start" className="d-inline mx-auto">
                    <Dropdown.Toggle id="dropdown-autoclose-true">
                     {selectedFilter.color}
                    </Dropdown.Toggle>

                    <Dropdown.Menu  className="browse_filter_dropdown">
                      {color.map((col) => (
                        <Dropdown.Item
                          onClick={() => {
                           // console.log(filter, "this is color filter", col);
                            setFilter({
                              filters: "color",
                              value: col,
                              original: products,
                            });
                            setSelectedFilter({
                              ...selectedFilter,
                              color:col
                            })
                          }}
                          href="#"
                        >
                          {" "}
                          {col}
                        </Dropdown.Item>
                      ))}
                    </Dropdown.Menu>
                  </Dropdown>
                </Col>

                <Col className="browse_filter_col" xs={12} sm={3} lg={2}>
                  <Dropdown  align="start"  className="d-inline mx-auto">
                    <Dropdown.Toggle id="dropdown-autoclose-true">
                    {selectedFilter.brand}
                    </Dropdown.Toggle>

                    <Dropdown.Menu 
                    style={{
                      transform:"translate3d(0px, 21.3333px, 0px) !important"
                    }}
                    >
                      {["Woods", "Woodland", "Woodsports", "ASKATINGMONK"].map(
                        (brand) => (
                          <Dropdown.Item
                          style={{
                            color: "#3b6f44"
                          }}
                            onClick={() => {
                             // console.log(filter, "this if filter", brand);
                              setFilter({
                                filters: "brand",
                                value: brand,
                                original: products,
                              });

                              setSelectedFilter({
                              ...selectedFilter,
                              brand:brand
                            })
                            }}
                            href="#"
                          >
                            {" "}
                            {brand}
                          </Dropdown.Item>
                        ))}
                    </Dropdown.Menu>
                  </Dropdown>
                </Col>
              </Row>
            </div>

            {filter != null  && filter.length>0 ? (
              <React.Fragment>
                <Row style={{
                  paddingLeft:"5%",
                  paddingRight:"2%"
                }}>
                  {filter.map((prod) => {
                  //  console.log("this is each prod", prod);
                    if(prod.productPicturesdto!=null  && prod.productPicturesdto.length>0){
                      return (
                      <Col
                      style={{
                        padding:"0px"
                      }}
                       lg={3} sm={6} xs={6}>
                        <ProductCarousal
                          productid={prod.productdtoId}
                          imgUrl={prod.productPicturesdto!=null && prod.productPicturesdto.length>0? prod.productPicturesdto[0].productPicUrl :"#"}
                          price={prod.productdtoPrice}
                          description={prod.productPicturesdto!=null && prod.productPicturesdto.length>0? prod.productPicturesdto[0].productPicName:"Product_name"}
                          offer={prod.productdtoOffers}
                          finalPrice={prod.productFinalPrice}
                          images={prod.productPicturesdto}
                          browse={true}
                          bestSeller={prod.bestSeller}
                          category={state.category}
                          for={state.for}
                        />
                      </Col>
                    );
                    
                    }
                  
                  })}
                </Row>
              </React.Fragment>
            ) : (
              <div style={{fontWeight:"500",fontSize:"25px"}}>no matching results</div>
            )}
          </React.Fragment>
        }
        <footer>
          <Footer />
       
        </footer>
      </React.Fragment>
    );
}
export default BrowseProducts