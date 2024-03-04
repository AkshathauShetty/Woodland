import { Col, Row } from "react-bootstrap"
import '../../assets/styles/orderDetails.css'
import useWindowsizes from "../../components/Windowsize"
import { updatedPrice } from "../../components/methods/computations"
import { useMemo } from "react"

const OrderDetails=(props)=>{

    const {isSmall,setSmall} = useWindowsizes()

    const getDate=(date)=>{
        let dateObject = new Date(Date.parse(date))
        return dateObject.toString().slice(3,15)
    }

    const newPrice=useMemo(()=>{
        return updatedPrice(props.product.productPrice,props.product.productOffers);
    },[props.product.productPrice,props.product.productOffers])

    return(

        <div className="order_details_container">
        {
            isSmall<900?
            <>
            <Row>
                <Col className="order_det_key"  sm={6}>
                    Order Placed
                </Col>
                <Col className="order_det_value"  sm={6}>
                    {
                        props.product.orderedDate!=null?
                        getDate(props.product.orderedDate)
                        :
                        ''
                    }
                </Col>
            </Row>
            <Row >
                <Col className="order_det_key"  sm={6}>
                    Order ID
                </Col>
                <Col className="order_det_value"  sm={6}>
                    {
                        props.product.orderId!=null?
                        props.product.orderId
                        :
                        ''
                    }
                </Col>
            </Row>
            <Row >
                <Col className="order_det_key"  sm={6}>
                    Status
                </Col>
                <Col className="order_det_value"  sm={6}>
                    {
                        props.product.orderStatus!=null?
                        props.product.orderStatus
                        :
                        'Success'
                    }
                </Col>
            </Row>
            <Row >
                <Col className="order_det_key"  sm={6}>
                    Total
                </Col>
                <Col className="order_det_value"  sm={6}>
                    {
                        props.product.productPrice!=null?
                        newPrice
                        :
                        '_'
                    }
                </Col>
            </Row>
            </>
            :
            <>
                <Row>
                    <Col lg={2}>
                        Order Placed
                    </Col>
                    <Col lg={2}>
                        Order ID
                    </Col>
                    <Col lg={2}>
                        Status
                    </Col>
                </Row>

                <Row>
                    <Col lg={2}>
                    {
                        props.product.orderedDate!=null?
                        getDate(props.product.orderedDate)
                        :
                        ''
                    }
                    </Col>
                    <Col lg={2}>
                    {
                        props.product.orderId!=null?
                        props.product.orderId
                        :
                        ''
                    }
                    </Col>
                    <Col lg={2}>
                    {
                        props.product.orderStatus!=null?
                        props.product.orderStatus
                        :
                        'Success'
                    }
                    </Col>
                </Row>
            </>

        }
           
        </div>
    )
}

export default OrderDetails