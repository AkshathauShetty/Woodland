import { faFacebook, faLinkedin, faTwitter, faYoutube } from "@fortawesome/free-brands-svg-icons"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import React, { useEffect, useState } from "react"
import { Col, Row } from "react-bootstrap"
import '../../assets/styles/Homestyle.css'


const Footer=()=>{

    const [isSmall,setSmall] = useState(window.innerWidth);


    const updateMedia = () => {
        setSmall(window.innerWidth);
      };
    
      useEffect(() => {
        window.addEventListener("resize", updateMedia);
        return () => window.removeEventListener("resize", updateMedia);
      });
    
      const alignlogo={
        textAlign:isSmall<=576? "center":"left"
      }

    return(
       <div className="footer_container">
            <div class="footer_logo" style={alignlogo}>
                <img  src="https://www.woodlandworldwide.com/images/woodland-footer.png" width="124" height="81"/>                    
            </div>
            

            { isSmall>576 ?
            
                <React.Fragment>
            <Row  className="category_col" style={{"textAlign":"left"}}>
                <Col sm={4} lg={2}>
                   <p><a className="footer_refs"    href="#">Shop Footwear</a></p> 
                   <p  > <a className="footer_ref"  href="#">Boots </a></p> 
                   <p> <a className="footer_ref"  href="#">Sneakers / Sports </a></p> 
                   <p> <a className="footer_ref"  href="#">Casuals</a></p>
                    <p><a  className="footer_ref" href="#">Formals </a></p>
                   <p> <a  className="footer_ref" href="#">Slippers/ Flip-Flops Footwear</a></p>
                   <p> <a className="footer_ref"  href="#">Sandals </a></p>
                   <p> <a  className="footer_ref" href="#">Safety Shoes </a></p>
                </Col>

                <Col sm={4} lg={2}>
                   <p><a className="footer_refs"  href="#">Shop Clothing Tops</a></p> 
                  <p> <a className="footer_ref"  href="#">Jackets</a></p>
                 <p>   <a  className="footer_ref" href="#">T-Shirtts / Sports </a></p>
                  <p>  <a className="footer_ref"  href="#">Shirts</a></p>
                  <p>  <a className="footer_ref"  href="#">Sweaters </a></p>
                   <p> <a  className="footer_ref" href="#">Sweatshirts & Hoodies</a></p>
                   <p> <a className="footer_ref"  href="#">Innerwear Tops </a></p>
                </Col>
                <Col sm={4} lg={2}>
                   <p> <a  className="footer_refs" href="#">Shop Accessories & Bags</a></p>
                   <p> <a className="footer_ref"  href="#">Hiking Backpacks</a></p>
                   <p> <a className="footer_ref"  href="#">Cross Body Bags / Sports </a></p>
                   <p> <a className="footer_ref"  href="#">Belts</a></p>
                   <p> <a className="footer_ref"  href="#">Socks </a></p>
                   <p> <a className="footer_ref"  href="#">Wallets/Cardholders</a></p>
                   <p> <a className="footer_ref"  href="#">Shoe Care </a></p>
                    <p><a className="footer_ref"  href="#">Trolley Bags </a></p>
                </Col>

                <Col md={12} lg={3}>
                    <div className="footer_content">
                    <p className="footer_contactus">Contact Us</p>
                    <p className="footer_contactus_content">Need help? Get in touch with us and we’ll be more than happy to guide you through!</p>
                
                    <a className="footer_phone_web"
                    href="care@woodlandworldwide.com">care@woodlandworldwide.com</a> <br/>
                    <a className="footer_phone_web" href="#">1800 103 3445</a>
                

                    <p className="footer_service_info">Woodland Customer Service Reps are available for inquiries Monday to Friday from 10AM to 6PM.</p>          
                    </div>
                </Col>

                <Col md={12} lg={3}>
                    <div style={{"marginTop":"1px",textAlign:"left"}}>
                    <p  className="footer_content footer_explore_content">
                    <span className="footer_explore">Always keep exploring more!</span><br></br>
                    Get alerts for new arrivals, offers, and more!</p>

                    <input
                    style={{"border":"none"}}
                    type="email"
                    className="footer_email femail_width"
                    placeholder="Email Address"
                    />

                    <button
                    style={{"border":"none","backgroundColor":"white","color":"green"}}
                    className="footer_submit fsubmit_width"
                    type="submit"
                    >Submit</button>

                    <p  className="email_desclaimer">By entering your email, you agree to our Terms of Service + Privacy Policy, including receipt of emails and promotions. You can unsubscribe at any time</p>
                    
                    </div>
                </Col>


            </Row> 
            {/* <div className="footer_content">
                <p className="footer_contactus">Contact Us</p>
                <p className="footer_contactus_content">Need help? Get in touch with us and we’ll be more than happy to guide you through!</p>
              
                <a className="footer_phone_web"
                 href="care@woodlandworldwide.com">care@woodlandworldwide.com</a> <br/>
                <a className="footer_phone_web" href="#">1800 103 3445</a>
               

                <p className="footer_service_info">Woodland Customer Service Reps are available for inquiries Monday to Friday from 10AM to 6PM.</p>          
            </div> */}
            </React.Fragment>
            
            : <hr></hr>}
            
           



            <div style={{"marginTop":"1px",textAlign:"left"}}>
                <p  className="footer_content footer_explore_content">
                <span className="footer_explore">Always keep exploring more!</span><br></br>
                Get alerts for new arrivals, offers, and more!</p>

                <input
                style={{"border":"none"}}
                type="email"
                className="footer_email femail_width"
                placeholder="Email Address"
                />

                <button
                style={{"border":"none","backgroundColor":"white","color":"green"}}
                className="footer_submit fsubmit_width"
                 type="submit"
                 >Submit</button>

                 <p  className="email_desclaimer">By entering your email, you agree to our Terms of Service + Privacy Policy, including receipt of emails and promotions. You can unsubscribe at any time</p>
                 
            </div>

            <div>

                {
                    isSmall>576 ? '':
                    <React.Fragment>
                        <Row>
                            <Col xs={12}>
                                <hr></hr>
                            </Col>
                        </Row>

                    <Row  className="footer_links">
                        <Col  className="footer_links" xs={6}>
                            <a  className="link_styles" >About Us </a>
                        </Col>

                        <Col xs={6}>
                            <a  className="link_styles" href="#"> Terms of Service</a> 
                        </Col>

                        <Col xs={6}>
                            <a className="link_styles" href="#">Privacy Policy</a> 
                        </Col>

                        <Col xs={6}>
                             <a  className="link_styles" href="#">Blogs</a> 
                        </Col>

                        <Col xs={6}>
                            <a className="link_styles" href="#">Refund policy</a>
                        </Col>

                        <Col xs={6}>
                            <a  className="link_styles" href="#"> Store Locator</a>
                        </Col>

                    </Row>

                    <Row>
                            <Col xs={12}>
                                <hr></hr>
                            </Col>
                    </Row>


                    </React.Fragment>
              }




                <Row>
                    
                    <Col xs={2}  sm={1} md={1}>
                    <FontAwesomeIcon icon="fa-brands fa-instagram" style={{color: "#f4f5f6",}} />
                    <svg xmlns="http://www.w3.org/2000/svg" height="16" width="14" viewBox="0 0 448 512">
                    <path fill="#f4f5f6" d="M224.1 141c-63.6 0-114.9 51.3-114.9 114.9s51.3 114.9 114.9 114.9S339 319.5 339 255.9 287.7 141 224.1 141zm0 189.6c-41.1 0-74.7-33.5-74.7-74.7s33.5-74.7 74.7-74.7 74.7 33.5 74.7 74.7-33.6 74.7-74.7 74.7zm146.4-194.3c0 14.9-12 26.8-26.8 26.8-14.9 0-26.8-12-26.8-26.8s12-26.8 26.8-26.8 26.8 12 26.8 26.8zm76.1 27.2c-1.7-35.9-9.9-67.7-36.2-93.9-26.2-26.2-58-34.4-93.9-36.2-37-2.1-147.9-2.1-184.9 0-35.8 1.7-67.6 9.9-93.9 36.1s-34.4 58-36.2 93.9c-2.1 37-2.1 147.9 0 184.9 1.7 35.9 9.9 67.7 36.2 93.9s58 34.4 93.9 36.2c37 2.1 147.9 2.1 184.9 0 35.9-1.7 67.7-9.9 93.9-36.2 26.2-26.2 34.4-58 36.2-93.9 2.1-37 2.1-147.8 0-184.8zM398.8 388c-7.8 19.6-22.9 34.7-42.6 42.6-29.5 11.7-99.5 9-132.1 9s-102.7 2.6-132.1-9c-19.6-7.8-34.7-22.9-42.6-42.6-11.7-29.5-9-99.5-9-132.1s-2.6-102.7 9-132.1c7.8-19.6 22.9-34.7 42.6-42.6 29.5-11.7 99.5-9 132.1-9s102.7-2.6 132.1 9c19.6 7.8 34.7 22.9 42.6 42.6 11.7 29.5 9 99.5 9 132.1s2.7 102.7-9 132.1z"/></svg>
                    </Col>

                    <Col xs={3}  sm={1} md={1}>
                    <FontAwesomeIcon icon={faFacebook} style={{color: "#f4f0f0",}} />
                     </Col>

                     <Col xs={2}   sm={1} md={1}>
                     <FontAwesomeIcon icon={faTwitter} style={{color: "#ffffff",}} />
                     </Col>

                     <Col xs={3}  sm={1} md={1}>
                     <FontAwesomeIcon icon={faYoutube} style={{color: "#ffffff",}} />
                     </Col>

                     <Col xs={2}  sm={1} md={1}>
                     <FontAwesomeIcon icon={faLinkedin} style={{color: "#ffffff",}} />
                     </Col>

                    {
                        isSmall<576 ? '':
                        
                            <Col xs={7}>
                                <hr></hr>
                            </Col>
                            
                        
                    }
                </Row>
                <br></br>

                {
                    isSmall<=576?'':
                   
                    <Row className="links_container">
                        <Col sm={2} lg={1}>
                           <a className="link_styles" href="#"> About Us</a>
                        </Col>

                        <Col sm={2} lg={1}>
                       <a className="link_styles" href="#"> Terms of Service</a>
                        </Col>

                        <Col sm={2} lg={1}>
                       <a className="link_styles" href="#"> Privacy Policy</a>
                        </Col>

                        <Col sm={2} lg={1}>
                       <a className="link_styles" href="#"> Blogs</a>
                        </Col>

                        <Col sm={2} lg={1}>
                       <a className="link_styles" href="#"> Refund policy</a>
                        </Col>

                        <Col sm={2} lg={1}>
                      <a className="link_styles" href="#">  Store Locator</a>
                        </Col>
                    </Row>
                }
            </div>

       </div>
    )
}


export default Footer