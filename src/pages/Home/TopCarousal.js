import React, { useState } from 'react';
import Carousel from 'react-bootstrap/Carousel';


const Topcarousal=()=> {
  const [index, setIndex] = useState(0);

  const handleSelect = (selectedIndex) => {
    setIndex(selectedIndex);
  };

  return (
    <React.Fragment>
        <Carousel className='top_carousal' data-bs-theme="dark" activeIndex={index} onSelect={handleSelect}>
          <Carousel.Item interval={1500}>
              <img
              src="https://www.woodlandworldwide.com/_next/image?url=https%3A%2F%2Fassets.woodlandworldwide.app%2Fwoodland-images%2FImages%2Fhero_banner-sale.png&w=1920&q=75"
              height="500"
              width="100%"
              />
          </Carousel.Item>
          <Carousel.Item interval={1500}>
          <img
              src="https://www.woodlandworldwide.com/_next/image?url=https%3A%2F%2Fassets.woodlandworldwide.app%2Fwoodland-images%2Flanding_page%2Fhero_banners%2Fhero_banner_2.png&w=1920&q=75"
              height="500"
              width="100%"
              />
          </Carousel.Item>
          <Carousel.Item interval={1500}>
          <img
              className="d-block w-200"
              src="https://www.woodlandworldwide.com/_next/image?url=https%3A%2F%2Fassets.woodlandworldwide.app%2Fwoodland-images%2Flanding_page%2Fhero_banners%2Fhero_banner_3.png&w=1920&q=75"
              height="500"
              width="100%"
              />
          </Carousel.Item>
        </Carousel>
    </React.Fragment>
  );
}

export default Topcarousal;