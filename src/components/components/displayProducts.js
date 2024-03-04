import axios from "axios";
import React, { useEffect, useState } from "react";
import Carousel from "react-multi-carousel";
import "react-multi-carousel/lib/styles.css";
import ProductCarousal from "../components/ProductCarousal";
import useGetrequests from "../customRequests/getrequest";

/**
 * Carousel containing products data (used in : Home page , search results)
 * @param {product details} props 
 * @returns 
 */
const Displayproducst = (props) => {
  const responsive = {
    superBig: {
      breakpoint: { max: 3000, min: 1000 },
      items: 5,
    },
    desktop: {
      breakpoint: { max: 1000, min: 800 },
      items: 3,
    },
    tablet: {
      breakpoint: { max: 800, min: 464 },
      items: 2,
    },
    mobile: {
      breakpoint: { max: 464, min: 0 },
      items: 1,
    },
  };

  const [index, setIndex] = useState(0);

  const handleSelect = (selectedIndex) => {
    setIndex(selectedIndex);
  };
 

  const [product, setProduct] =  useGetrequests(props.url);

  return (
    <React.Fragment>
      <Carousel responsive={responsive} infinite={true}>
        {product == null ? (
          <p>no items to display</p>
        ) : (
          product.map((prod) => {
            return (
              <ProductCarousal
                productid={prod.productdtoId}
                imgUrl={prod.productPicturesdto[0]!=null? prod.productPicturesdto[0].productPicUrl:'#'}
                price={prod.productdtoPrice}
                description={prod.productPicturesdto[0]!=null? prod.productPicturesdto[0].productPicName: ''}
                offer={prod.productdtoOffers}
                finalPrice={prod.productFinalPrice}
                images={prod.productPicturesdto}
                bestSeller={prod.bestSeller}
              />
            );
          })
        )}
      </Carousel>
    </React.Fragment>
  );
};

export default Displayproducst;
