import { InfoWindow } from '@react-google-maps/api';
import React, { useEffect, useState } from 'react';

import { InfoWrapper, Nota } from './styles';

const CInfoWindowFilter = (props) => {
  const placeData = props.data;
  const [image, setImage] = useState(null);

  useEffect(() => {
    setImage(placeData.imageURL);
  }, []);

  placeData.laLng.lat += 0.00078;

  return (
    <InfoWindow
      position={placeData.latLng}
      onCloseClick={() => {
        props.handleInfoClose();
      }}
    >
      <InfoWrapper>
        {image ? <img className="cape" src={image} /> : <div className="cape-unavailable"></div>}

        <div className="content">
          {placeData.localName && <h1 className="name">{placeData.localName}</h1>}
          {placeData.vicinity ? <p className="endereco">{placeData.vicinity}</p> : <></>}
          {placeData.rating ? <Nota>{placeData.rating}</Nota> : <></>}
        </div>
      </InfoWrapper>
    </InfoWindow>
  );
};

export default CInfoWindowFilter;
