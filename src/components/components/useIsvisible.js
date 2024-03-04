import { useEffect, useState } from "react"

const useIsvisible=(ref)=>{
    const [isIntersecting, setIsinteresecting] = useState(false)

    useEffect(()=>{
        const observer= new IntersectionObserver(([entry])=>
        setIsinteresecting(entry.isIntersecting))

        observer.observe(ref.current);
        return ()=>{
            observer.disconnect();
        };
    },[ref]);
    
    return isIntersecting;

}

export default useIsvisible