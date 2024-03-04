import React from "react"
import { Col, Offcanvas, OffcanvasBody, OffcanvasHeader, OffcanvasTitle, Row } from "react-bootstrap"
import '../../assets/styles/prodinfo.css'
import ProdInfocanvs from "./prodInfoCanva"
import useWindowsizes from "../../components/Windowsize"

/**
 * Information on the product 
 * @param {productInformation} props 
 * @returns Off canvas contaning product details
 */
const ProductimportInfo=(props)=>{

    const { isSmall, setSmall } = useWindowsizes();

    const offcanvastyle = {
      "--bs-offcanvas-width": isSmall < 900 ? "100%" : "45%",
    };

    const prodHeader = [
      "Product Code",
      "Product description",
      "Color",
      "Size",
      "Size in cm",
      "MRP",
      "Materials Used",
      "Designed For",
      "Packaging Quality",
      "Origin Country",
      "Marketed By",
      "Care Email",
    ];
    return (
      <React.Fragment>
        <Offcanvas style={offcanvastyle} {...props} placement="end">
          <OffcanvasHeader className="prodinfo_offcanvas_header" closeButton>
            <OffcanvasTitle className="prodinfo_manufacturedet_offcanvas">
              IMPORT, MANUFACTURING & PACKAGING INFO
            </OffcanvasTitle>
          </OffcanvasHeader>

          <OffcanvasBody className="productinfo_offcanva_body">
           

            {prodHeader.map((item) => {
              return (
                <ProdInfocanvs
                  title={item}
                  data="FLC0GO044181A (Art No : LB 4418122)"
                />
              );
            })}
          </OffcanvasBody>
        </Offcanvas>
      </React.Fragment>
    );
}

export default ProductimportInfo