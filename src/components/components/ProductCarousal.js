
import React, { useCallback } from "react"
import { Card, Image } from "react-bootstrap"
import { useNavigate } from "react-router-dom";
import ProductImageCarousal from "./ProductImageCarousal";
import '../../assets/styles/productCarousal.css'

/**
 * Card with image information used in Home page products carousel , search results page
 * @param {product info} props 
 * @returns 
 */
const  ProductCarousal =(props)=>{
    const navigate = useNavigate()

    const offer=useCallback((price,offer)=>{
        return price-(price*offer/100);
    },[props])
    

    return (
      <div
        style={{
          marginLeft: "5%",
          marginRight: "3%",
        }}
        className="carousel_data"
      >
        {/* {console.log("data receved:", props)} */}
        <Card
          className="carousal_products_display"
          style={{
            margin: "1%",
            border: "none",
          }}
        >
          {/* <div  style={{"backgroundColor":"#e0e0e0"}}> */}
          {props.browse ? (
            <ProductImageCarousal product={props} images={props.images} />
          ) : (
            <div className="slider_div" style={{ backgroundColor: "#e0e0e0" }}>
              {props.offer > 1 ? (
                <span className="carousel_offer_top">{props.offer} %</span>
              ) : (
                ""
              )}
              <img
                onClick={(e) => {
                  e.preventDefault();
                  navigate("/productInfo", {
                    state: {
                      product: props,
                    },
                  });
                  window.scrollTo(0, 0);
                }}
                style={{ mixBlendMode: "darken", height: "200px" }}
                className="img img-fluid"
                src={props.imgUrl}
              />
              {props.bestSeller == "True" ? (
                <span className="carousel_best_seller_bottom">
                  ★ Bestseller
                </span>
              ) : (
                ""
              )}
            </div>
          )}
          <Card.Body
            style={{
              padding: "0px",
            }}
          >
            <Card.Text
              className="carousal_prod_info"
              style={{ textAlign: "left", marginBottom: "2px" }}
            >
              {props.description}
            </Card.Text>
            <Card.Text style={{ textAlign: "left" }}>
              <p>
                {" "}
                <del> ₹{props.price} </del> &nbsp;
                <b>₹{offer(props.price, props.offer)}</b>
              </p>
            </Card.Text>
          </Card.Body>
        </Card>
      </div>
    );
}

export default ProductCarousal