import { createContext, useEffect, useState } from "react";




const myContextProvider = createContext(window.innerWidth);

const useWindowsizes =()=>{
    const [isSmall,setSmall] = useState(window.innerWidth);


    const updateMedia = () => {
        setSmall(window.innerWidth);
      };
    
      useEffect(() => {
        window.addEventListener("resize", updateMedia);
        return () => window.removeEventListener("resize", updateMedia);
      });

      return {isSmall,setSmall}
}

export default useWindowsizes