import { Col, Offcanvas, OffcanvasBody, OffcanvasHeader, OffcanvasTitle, Row } from "react-bootstrap"
import React from "react"
import useWindowsizes from "../../components/Windowsize";

/**
 * Provides the size details of the product
 * @param {productCategory} props 
 * @returns 
 */
const ProdinfoSizeGuide = (props) => {
  const { isSmall, setSmall } = useWindowsizes();

  const offcanvastyle = {
    "--bs-offcanvas-width": isSmall < 900 ? "100%" : "45%",
  };

  return (
    <React.Fragment>
      <Offcanvas style={offcanvastyle} {...props} placement="end">
        <Row style={{ padding: "3%" }}>
          <Col xs={2}>
            <OffcanvasHeader closeButton></OffcanvasHeader>
          </Col>
          <Col xs={10}>
            <OffcanvasTitle
              style={{
                color: "rgba(48, 48, 48, 0.92)",
              }}
            >
              Size Guide
            </OffcanvasTitle>
            <p
              tyle={{
                color: "rgb(37, 16, 0)",
              }}
            >
              Select the size that suits you best
            </p>
          </Col>
          <hr></hr>
        </Row>

        <OffcanvasBody style={{ textAlign: "left", padding: "10%" }}>
          <div>
            <p>WOMEN'S FOOTWEAR SIZE</p>
            <Row>
              <Col>UK SIZE</Col>
            </Row>
          </div>
        </OffcanvasBody>
      </Offcanvas>
    </React.Fragment>
  );
};

export default  ProdinfoSizeGuide