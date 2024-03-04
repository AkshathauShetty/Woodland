import { useEffect, useReducer } from "react"
import { deleteItem } from "../../pages/Cart/addTocart";
import { request } from "../variables/variables";
import { errorMessage } from "../methods/computations";
import { useNavigate } from "react-router-dom";

const reducer = (state, action) => {
  while (action != null) {
    switch (action.function) {
      case "save":
        return action.data;
        break;
      case "filter":
       // console.log("inside the reducer function", action.index);
        return state.filter((item, index) => {
          if (index != action.index) {
            return item;
          } else {
       //     console.log("dleeting the item---->", item);
            deleteItem(item);
          }
        });
        break;
      case "incQty":
      //  console.log("inside the increment  updatecount");
        return state.map((item, index) => {
          if (index == action.index) {
         //   console.log(item);

            return { ...item, productQuantity: item.productQuantity + 1 };
          } else {
            return item;
          }
        });
        break;
      case "decQty":
      //  console.log("inside the  updatecount");
        return state.map((item, index) => {
          if (index == action.index) {
            //.log(item);
            return { ...item, productQuantity: item.productQuantity - 1 };
          } else {
            return item;
          }
        });
        break;
      // case "remove":return state.filter((item,index)=>deleteItem(item));break;
      case "error" : return [{"error":action.error}]
      default:
        return state;
    }
  }
};


const useGetcart = (cartId,setIsLoading) => {
  const [cartdatas, setCartdatas] = useReducer(reducer, []);
  const token = localStorage.getItem(localStorage.getItem("phone"));
  const navigate = useNavigate()


  useEffect(() => {
   // console.log("useeeee ->cart", cartId);
    if (cartId > 0) {
      getCartDetails(cartId);
    }
  }, []);

  const getCartDetails = async(cartId) => {
   // console.log("inside getcartdetails");
    try{
      const response = await 
      request
      .get(`/displayCart/${cartId}`, {
        headers: {
          "Content-Type": "application/json",
          "Access-Control-Allow-Header": "Content-Type",
          "Access-Control-Allow-Origin": "*",
          Authorization: `Bearer ${token}`,
        },
      });
      if(response.status==200){
        setIsLoading(false)
        setCartdatas({
                  function: "save",
                  data: response.data,
                });
      }
    //  console.log('await request.',response)
      setIsLoading(false)
    }
    catch(error){
      if(error.response.status==500){
        setIsLoading(false)
        // errorMessage(error.response.data)
        navigate('/home')

        
        // setCartdatas({
        //   function: "error",
        //   error: error.response.data,
        // });
   //  console.log('await error', error.response)
    }
  }
  };

  return [cartdatas, setCartdatas];
};

export default useGetcart