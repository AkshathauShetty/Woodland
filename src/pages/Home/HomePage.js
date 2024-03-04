import React, { useReducer, useState } from "react"
import Woodlandheader from "../../components/Header/Header.js"
import Topcarousal from "./TopCarousal"
import Homecategory from "./staticTopCategory.js"
import Displayproducst from "../../components/components/displayProducts.js"
import Belowstatic from "./staticBottomCategory.js"
import Bottomstatictwo from "./staticBottom.js"
import Footer from "../../components/footer/footer.js"
import BrowseProducts from "../../components/components/browseProducts.js"
import UserInfoContext from "../../components/Header/userInfoContext.js"

export const MypageContext = React.createContext('Home')


const DESTINATION={
    "HOME":"home",
    "BROWSE_PRODUCTS":"browse_products",
    "PRODUCT_DISPLAYS" : "display_products"
}

const reducer=(state,action)=>{

    switch(action.destination){
       case 'HOME':return state;
       case 'BROWSE_PRODUCTS' : return state; 
       case 'PRODUCT_DISPLAYS' : return state;
       default : return state;

    }
}


const Homepage =()=>{

    // const [currentPage,setCurrentPage]=useState("home")

    // const [currentPages,setCurrentPages]=useReducer(reducer, "home")

    return(
        <React.Fragment>
        <header>
            <Woodlandheader/> 
        </header>
             

                <body>   
                    <Topcarousal/>
                    <br></br>
                    <Homecategory/>
                    <br></br>
                    <Displayproducst
                        url='getProductsByCategory/Clothing Tops/Men'
                    />
                    <Belowstatic/>
                    <br>
                    </br>
                    <Displayproducst
                        url='getProductsByCategory/Footwear/Men'
                    />
                    <br></br>
                    <Bottomstatictwo/>
                </body>
            <footer>
                <Footer/>
            </footer>
           
        </React.Fragment>
    )
}

export default Homepage