import React, { useCallback, useContext, useEffect, useState } from 'react';
import { GoogleMap, LoadScript, Marker, StandaloneSearchBox, DirectionsService, DirectionsRenderer } from '@react-google-maps/api';
import mapStyles from '../../Organisms/ContainerMaps/mapStyles';
import Axios from 'axios';
import StateContext from '../../../StateContext';
import saved_place from '../../../assets/push_pin.svg';

const libraries = ['places', 'directions'];

const mapContainerStyle = {
  width: '100%',
  height: '100%',
};

const options = {
  styles: mapStyles, // mais estilos em SnazzyMaps
  disableDefaultUI: true,
  zoomControl: true,
};

function PostMap({ data, center }) {
  const [directionsResponse, setDirectionsResponse] = useState();
  const [origin, setOrigin] = useState();
  const [destination, setDestination] = useState();
  const [map, setMap] = useState(null);
  const [trip, setTrip] = useState([
    {
      id: 'aa5fd690b3db7bb55eb06a5f6e',
      latMatch: '-3.222',
      lngMatch: '-34.533',
      latDestiny: '-3.115',
      lngDestiny: '-32.523',
      destiny: 'Avenida Paulista ; Rua Horácio de Carvalho',
      idUser: '5fc556949197b03c48816a9f',
    },
  ]);

  const [local, setLocal] = useState();

  const appState = useContext(StateContext);

  const directionsCallback = (response) => {
    setDestination('');
    setOrigin('');
    setDirectionsResponse(response);
  };

  const mapOnLoad = useCallback((map) => {
    setMap(map);
  }, []);

  useEffect(() => {
    if (data && data.isItinerario) {
      console.log('IS ITINERARIO', data);
      const latOBJD = parseFloat(data.trip.latDestiny);
      const lngOBJD = parseFloat(data.trip.lngDestiny);

      const centerOBJD = {
        lat: latOBJD,
        lng: lngOBJD,
      };

      console.log('CENTER OBJETAAAAAAAAA', centerOBJD);
      const latOBJO = parseFloat(data.trip.latMatch);
      const lngOBJO = parseFloat(data.trip.lngMatch);

      const centerOBJO = {
        lat: latOBJO,
        lng: lngOBJO,
      };

      setDestination(centerOBJD);
      setOrigin(centerOBJO);
    } else if (data) {
      const latOBJD = parseFloat(data.trip.latDestiny);
      const lngOBJD = parseFloat(data.trip.lngDestiny);

      const centerOBJD = {
        lat: latOBJD,
        lng: lngOBJD,
      };

      console.log('CENTER OBJETAAAAAAAAA NÃO ITINERARIO', centerOBJD);

      const latOBJO = parseFloat(data.trip.latMatch);
      const lngOBJO = parseFloat(data.trip.lngMatch);

      const centerOBJO = {
        lat: latOBJO,
        lng: lngOBJO,
      };

      setDestination(centerOBJD);
      setOrigin(centerOBJO);
    } else {
      return;
    }
  }, []);

  useEffect(() => {
    let userId = null;
    if (data) {
      console.log('DATAAAAAAAAAAAA', data);
      userId = data.userId;
    }

    if (userId) {
      Axios.get(`trip/${userId}`, { headers: { authorization: appState.user.jwtkey } })
        .then((e) => {
          setTrip(e.data.content);
        })
        .catch((e) => {
          console.error(e);
        });
    }
  }, []);

  useEffect(() => {
    Axios.get(`filter/${trip[0].id}`, { headers: { authorization: appState.user.jwtkey } })
      .then((e) => {
        setLocal(e.data);
      })
      .catch((e) => {
        console.error(e);
      });
  }, []);

  return (
    <div style={{ width: '100%', height: '100%' }}>
      <LoadScript googleMapsApiKey={process.env.REACT_APP_GOOGLE_MAPS_API_KEY} libraries={libraries}>
        <GoogleMap mapContainerStyle={mapContainerStyle} onLoad={mapOnLoad} center={center} zoom={14} options={options}>
          {local ? (
            local.map((a, ind) => {
              console.log('MARCADOR', a);

              const latOBJ = parseFloat(a.latitude);
              const lngOBJ = parseFloat(a.longitude);

              const centerOBJ = {
                lat: latOBJ,
                lng: lngOBJ,
              };
              return <Marker key={ind} title={a.localName} icon={saved_place} animation={2} position={centerOBJ} />;
            })
          ) : (
            <></>
          )}
          {destination && origin ? (
            <DirectionsService
              callback={directionsCallback}
              options={{
                destination: destination,
                origin: origin,
                travelMode: 'DRIVING',
              }}
            />
          ) : (
            <DirectionsService callback={directionsCallback} options={{}} />
          )}

          {directionsResponse ? <DirectionsRenderer directions={directionsResponse} /> : <DirectionsRenderer options={{}} />}
        </GoogleMap>
      </LoadScript>
    </div>
  );
}

export default PostMap;
