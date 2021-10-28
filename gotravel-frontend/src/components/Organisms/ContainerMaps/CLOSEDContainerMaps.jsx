import React from 'react';
import { GoogleMap, useLoadScript, Marker, InfoWindow } from '@react-google-maps/api';
import mapStyles from './mapStyles';
import { formatRelative } from 'date-fns';
import usePlacesAutocomplete, { getGeocode, getLatLng } from 'use-places-autocomplete';
import { Combobox, ComboboxInput, ComboboxPopover, ComboboxList, ComboboxOption } from '@reach/combobox';
import { Logo, SearchDiv, LocateButton } from './ContainerMaps.style';
import { useState } from 'react';
import { useCallback } from 'react';

import Compassso from '../../../assets/compass-icon.png';

const libraries = ['places'];
const mapContainerStyle = {
  width: '100vw',
  height: '100vh',
};

const center = {
  lat: 43.653225,
  lng: -79.383186,
};

const options = {
  styles: mapStyles, // mais estilos em SnazzyMaps
  disableDefaultUI: true,
  zoomControl: true,
};

const ContainerMaps = () => {
  const { isLoaded, loadError } = useLoadScript({
    googleMapsApiKey: process.env.REACT_APP_GOOGLE_MAPS_API_KEY,
    libraries,
  });
  const [markers, setMarkers] = useState([]);
  const [selected, setSelected] = useState(null);

  const onMapClick = useCallback((event) => {
    setMarkers((current) => [
      ...current,
      {
        lat: event.latLng.lat(),
        lng: event.latLng.lng(),
        time: new Date(),
      },
    ]);
  }, markers);

  const mapInstance = React.useRef(); // Referencia do map para evitar renderizar de novo
  const onMapLoad = useCallback((map) => {
    mapInstance.current = map;
  }, []);

  const panTo = useCallback(({ lat, lng }) => {
    mapInstance.current.panTo({ lat, lng });
    mapInstance.current.setZoom(14);
  }, []);

  if (loadError) return 'Erro carregando o google maps';
  if (!isLoaded) return 'Carregando o maps';

  return (
    <div>
      {/* <Logo /> */}

      <Search panTo={panTo} />
      <Locate panTo={panTo} />

      <GoogleMap mapContainerStyle={mapContainerStyle} zoom={8} center={center} options={options} onClick={onMapClick} onLoad={onMapLoad}>
        {markers.map((marker) => (
          <Marker
            key={marker.time.toISOString()}
            position={{ lat: marker.lat, lng: marker.lng }}
            // icon={{
            //     url: '/icone_aqui.svg',
            //     scaledSize: new window.google.maps.Size(30,30)
            // }}
            onClick={() => {
              setSelected(marker);
            }}
          />
        ))}

        {selected ? (
          <InfoWindow
            position={{ lat: selected.lat, lng: selected.lng }}
            onCloseClick={() => {
              setSelected(null);
            }}
          >
            <div>
              <h2>Cuzinho solto aqui</h2>
              <p>Data: {formatRelative(selected.time, new Date())}</p>
            </div>
          </InfoWindow>
        ) : null}
      </GoogleMap>
    </div>
  );
};

function Locate({ panTo }) {
  return (
    <LocateButton
      onClick={() => {
        navigator.geolocation.getCurrentPosition(
          (position) => {
            panTo({
              lat: position.coords.latitude,
              lng: position.coords.longitude,
            });
          },
          () => null
        );
      }}
    >
      <img src={Compassso} alt="Compassao - Localize eu!" />
    </LocateButton>
  );
}

function Search({ panTo }) {
  const {
    ready,
    value,
    suggestions: { status, data },
    setValue,
    clearSuggestions,
  } = usePlacesAutocomplete({
    requestOptions: {
      location: {
        lat: () => 43.653225,
        lng: () => -79.383186,
      },
      radius: 200 * 1000, //200 kilometros
    },
  });

  return (
    <SearchDiv>
      <Combobox
        onSelect={async (address) => {
          setValue(address, false);
          clearSuggestions();

          try {
            const results = await getGeocode({ address: address });
            const { lat, lng } = await getLatLng(results[0]);
            panTo({ lat, lng });
            console.lng(lat, lng);
          } catch (error) {
            console.log('Erro na linha 121, ContainerMaps.jsx');
          }
        }}
      >
        <ComboboxInput
          value={value}
          onChange={(event) => {
            setValue(event.target.value);
          }}
          disabled={!ready}
          placeholder="Enter an address"
        />
        <ComboboxPopover>
          <ComboboxList>{status === 'OK' && data.map(({ id, description }) => <ComboboxOption key={id} value={description} />)}</ComboboxList>
        </ComboboxPopover>
      </Combobox>
    </SearchDiv>
  );
}

export default ContainerMaps;
