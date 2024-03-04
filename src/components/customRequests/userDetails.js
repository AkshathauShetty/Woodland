import { useState } from "react"

export const userState= {
    "firstname":null,
    "lastname":null,
    "email":null,
    "phone":null,
     "dob" :null,
    "gender":null,
    "password":null,
    "token":null,
    "isLogged":false

}
const useUserdetail=()=>{
    const[userDetail,setUserDetail] = useState(userState)

    return [userDetail,setUserDetail] 
}

export default useUserdetail