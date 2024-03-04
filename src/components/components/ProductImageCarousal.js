import React, { useEffect, useState } from "react"
import { Image } from "react-bootstrap"
import { Carousel } from "react-responsive-carousel"
import "react-alice-carousel/lib/alice-carousel.css";
import '../../assets/styles/ProductImageCarousal.css'
import "react-responsive-carousel/lib/styles/carousel.min.css"; // requires a loader
import { useNavigate } from "react-router-dom";

/**
 *Product  Carousel used  search results page for each product 
 * @param {product details} props 
 * @returns 
 */

const ProductImageCarousal=(props)=>{
    const [image,setImage]=useState(props.images)
    const navigate = useNavigate()
    return (
      <div>
        {image != null ? (
          <>
            <Carousel
              infiniteLoop={true}
              thumbWidth="50"
              showIndicators={false}
              showStatus={false}
              showArrows={true}
              height="200px"
              autoPlay={false}
              axis="horizontal"
            >
              {image.map((img) => {
                return (
                  <div
                    className="slider_div"
                    onClick={(e) => {
                      e.preventDefault();
                      navigate("/productInfo", {
                        state: {
                          product: props.product,
                        },
                      });
                      window.scrollTo(0, 0);
                    }}
                  >
                    {props.product.offer > 1 ? (
                      <span className="carousel_offer_top">
                        {props.product.offer} %
                      </span>
                    ) : (
                      ""
                    )}
                    <img
                      style={{ mixBlendMode: "darken" }}
                      className="img img-fluid"
                      src={img.productPicUrl}
                    />
                    {props.product.bestSeller == 'True' ? (
                      <span className="carousel_best_seller_bottom">
                        ★ Bestseller
                      </span>
                    ) : (
                      ""
                    )}
                  </div>
                );
              })}
            </Carousel>
          </>
        ) : (
          ""
        )}
      </div>
    );

}

export default ProductImageCarousal