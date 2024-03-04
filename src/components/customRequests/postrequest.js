import { useEffect, useState } from "react"


const usePostreq = (props) => {
  const [user, setUser] = useState(props.userinfo);

  const register = () => {
    const url = `/props.url`;
    request
      .post(
        url,
        {
          firstname: user.firstname,
          lastname: user.lastname,
          email: user.email,
          phone: user.phone,
          dob: user.dob,
          gender: user.gender,
        },
        {
          headers: {
            "Content-Type": "application/json",
            "Access-Control-Allow-Header": "Content-Type",
            "Access-Control-Allow-Origin": "*",
          },
        }
      )
      .then((response) => {
       // console.log("response data:", response.data);
        setPost(response.data);
      //  console.log("userpost: ", userpost);
      });
  };

  return [user, setUser];
};

export default usePostreq