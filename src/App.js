
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './App.css';
import Homepage from './pages/Home/HomePage';
import BrowseProducts from './components/components/browseProducts';
import ProductInfo from './pages/Products/productInfo';
import Myorders from './pages/MyOrders/myorders';
import Addtocart from './pages/Cart/addTocart';
import OrderCheckout from './pages/OrderProduct/orderCheckout';
import UserInfoContext from './components/Header/userInfoContext';
import useUserdetail from './components/customRequests/userDetails';


function App() {
  
  const [userDetail,setUserDetail] = useUserdetail()

  return (
    <div className="App">

    <UserInfoContext.Provider value={[userDetail,setUserDetail]}>
          <BrowserRouter>
              <Routes>
                  <Route
                      exact
                      path="/"
                      element={ <Homepage/>}
                  />
                    <Route
                      exact
                      path="/home"
                      element={ <Homepage/>}
                  />
                  <Route
                      exact
                      path="/browseProducts"            
                      element={<BrowseProducts
                                keyword='ackets'/> }
                  />
                  
                  <Route
                      exact
                      path="/productInfo"            
                      element={<ProductInfo/> }
                  />
                    <Route
                      exact
                      path="/myOrders"            
                      element={<Myorders
                                /> }
                  />
                  <Route
                    exact 
                    path="/cart"
                    element={<Addtocart/>}
                  />
                  
                  <Route
                    exact 
                    path="/checkout"
                    element={<OrderCheckout/>}
                  />

              </Routes>
          </BrowserRouter>
    </UserInfoContext.Provider>
     
    </div>
  );
}

export default App;
