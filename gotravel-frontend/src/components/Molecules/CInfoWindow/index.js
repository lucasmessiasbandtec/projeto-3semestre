import { InfoWindow } from '@react-google-maps/api';
import Axios from 'axios';
import React, { useContext, useEffect, useState } from 'react';
import StateContext from '../../../StateContext';

import { InfoWrapper, Nota } from './styles';

const CInfoWindow = (props) => {
  const placeData = props.data;
  const [image, setImage] = useState(null);

  const appState = useContext(StateContext);

  useEffect(() => {
    setImage(null);

    //Function para requisitar imagens
    function requestImage(photoreference, width, height) {
      if (!photoreference && !width && !height) {
        return;
      }

      // const url = `https://maps.googleapis.com/maps/api/place/photo?key=AIzaSyBw46FEvXL1fBBgw8bocxI-fYTcva5yTeQ&photoreference=${photoreference}&maxwidth=${width}&maxheight=${height}`;
      const url = `/trip/place_image/${photoreference}/${width}/${height}`;

      // return Axios.get('https://cors-anywhere.herokuapp.com/' + url)
      return Axios.get(url, { headers: { authorization: appState.user.jwtkey } })
        .then((response) => {
          return response;
        })
        .catch((error) => {
          console.log(error);
        });
    }

    if (placeData.photos != null && placeData.photos != undefined) {
      requestImage(placeData.photos[0].photo_reference, placeData.photos[0].width, placeData.photos[0].height)
        .then((response) => {
          setImage(response.data);
        })
        .catch((thrown) => {
          if (Axios.isCancel(thrown)) {
            console.log('Requisição de imagem cancelada', thrown.message);
          }
        })
        .catch((error) => console.error(error));
    }
  }, [placeData]);

  return (
    <InfoWindow
      position={placeData.geometry.location}
      onCloseClick={() => {
        props.handleInfoClose();
      }}
    >
      <InfoWrapper>
        {image ? <img className="cape" src={image} /> : <div className="cape-unavailable"></div>}

        <div className="content">
          {placeData.name && <h1 className="name">{placeData.name}</h1>}
          {placeData.vicinity ? <p className="endereco">{placeData.vicinity}</p> : <></>}
          {placeData.rating ? <Nota>{placeData.rating}</Nota> : <></>}
        </div>
        <button
          onClick={() => {
            props.saveCallback({
              vicinity: placeData.vicinity,
              localName: placeData.name,
              latLng: placeData.geometry.location,
              imageURL: image ? image : '',
            });
          }}
          className="save"
        >
          Salvar local
        </button>
      </InfoWrapper>
    </InfoWindow>
  );
};

export default CInfoWindow;
