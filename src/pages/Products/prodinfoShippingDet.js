import { Col, Row } from "react-bootstrap"

/**
 * Details on the product 
 * @returns 
 */

export const ProdinforProddet=()=>{
    return (
      <>
        <Row>
          <Col xs={12}>
            <div className="prodinfo_minusdata">
              <p
                style={{
                  fontSize: "13px",
                }}
              >
                A refreshing deodorant by Woodland with Mandarin and black
                pepper notes that neutralizes all odor and keeps you fresh on
                all your outdoor adventures.
              </p>
            </div>
          </Col>
          <Col className="prodinfo_minusdata" xs={4}>
            <div
              style={{
                backgroundColor: "#edededd9",
                padding: "2px",
                textAlign: "center",
                fontSize: "16px",
              }}
              className="form_field"
            >
              FABRIC
              <div>NA</div>
            </div>
          </Col>
        </Row>
      </>
    );
}

/**
 * shipping details of the product - static
 * @returns 
 */
const ProdinfoShippingdet=()=>{
    return (
      <Row className="prodinfo_minusdataship">
        <Col className="prodinfo_shippingdets" xs={12}>
          Shipping Days
        </Col>
        <Col className="prodinfo_shippingRules" xs={12}>
          Your product will be delivered between 3-7 working days
        </Col>
        <Col className="prodinfo_shippingdets" xs={12}>
          Return and Exchange
        </Col>
        <Col className="prodinfo_shippingRules" xs={12}>
          Woodland may accept returns on apparel and footwear for account credit
          only
        </Col>
        <Col className="prodinfo_shippingRules">
          <ul>
            <li>
              For this purpose woodland must receive the merchandise within 15
              days from the date it was shipped to you
            </li>
            <li>
              Items must be unused, unworn, unwashed and undamaged by you.
            </li>
            <li>
              Goods will be returned only if they are returned in their original
              packaging.
            </li>
            <li>
              Goods once sold can only be exchanged for replacement or a store
              credit if they meet our terms and conditions.
            </li>
            <li>
              Since we keep limited inventory and do not always have all sizes
              available and under such circumstances the amount paid by you can
              be used by you whenever you shop with us next time.
            </li>
            <li>
              To return an item, the customer must write to us at
              care@woodlandworldwide.com, a prompt response is assured to such
              mails, for more details please read the FAQ’s
            </li>
          </ul>
        </Col>
      </Row>
    );
}

export default ProdinfoShippingdet