import { Col, Row } from "react-bootstrap";
import "../../assets/styles/prodinfo.css";

/**
 * used by the product info canvas 
 * @param {product details} props 
 * @returns a row containing 2 columns with product title and description
 */
const ProdInfocanvs = (props) => {
  return (
    <Row className="prodinfo_productDetails">
      <Col
        style={{
          fontSize: "14px",
          paddingLeft: "1%",
        }}
        xs={12}
        lg={4}
      >
        {props.title}
      </Col>

      <Col
        style={{
          fontSize: "14px",
          fontWeight: "600",
          marginTop: "1%",
          paddingLeft: "1%",
        }}
        xs={12}
        lg={7}
      >
        {props.data}
      </Col>
    </Row>
  );
};

export default ProdInfocanvs;
