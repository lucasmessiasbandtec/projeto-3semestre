import React, { useCallback, useContext, useEffect, useState } from 'react';
import { GoogleMap, LoadScript, Marker, StandaloneSearchBox, DirectionsService, DirectionsRenderer } from '@react-google-maps/api';
import CInfoWindow from '../../Molecules/CInfoWindow';
import CInfoWindowFilter from '../../Molecules/CInfoWindowFilter';
import StateContext from '../../../StateContext';
import FilterList from '../FilterList';
import { MapWrapper } from './ContainerMaps.style';
import Axios from 'axios';
import mapStyles from './mapStyles';
import './style.css';
import TripService from './trip.service';
import Modali, { useModali } from 'modali';

import you_icon from '../../../assets/person_pin.svg';
import saved_place from '../../../assets/push_pin.svg';
import nearby_places from '../../../assets/place.svg';
import farmacia from '../../../assets/pharmacy.svg';
import restaurante from '../../../assets/restaurant.svg';
import bares from '../../../assets/beer.svg';
import hospitais from '../../../assets/hospital.svg';
import hoteis from '../../../assets/hotel.svg';
import estacionamentos from '../../../assets/estacionamento.svg';
import parques from '../../../assets/Parques.svg';
import mapmarker from '../../../assets/mapmarker.svg';
import flag from '../../../assets/flag.svg';
import minus from '../../../assets/minus.svg';
import plus from '../../../assets/plus.svg';
import icon_save from '../../../assets/save_alt.svg';
import spinnersvg from '../../../assets/spinner-solid.svg';
import { Redirect } from 'react-router-dom';

const libraries = ['places', 'directions'];

const mapContainerStyle = {
  width: '100%',
  height: '100%',
};

const center = {
  lat: -3.745,
  lng: -38.523,
};

const options = {
  styles: mapStyles, // mais estilos em SnazzyMaps
  disableDefaultUI: true,
  zoomControl: true,
};

const CustomMarker = ({ icon, position }) => {
  return <Marker position={position} />;
};

const ContainerMaps = () => {
  const [filterMenu, setFilterMenu] = useState(false);
  const [filterSelected, setFilterSelected] = useState(false);
  const [map, setMap] = useState(null);
  const [markerUser, setMarkerUser] = useState();
  const [marker, setMarker] = useState();
  const [userLocation, setUserLocation] = useState(null);
  const [partida, setPartida] = useState(null);
  const [destino, setDestino] = useState(null);
  const [directionsOrigin, setDirectionsOrigin] = useState('');
  const [directionsDestiny, setDirectionsDestiny] = useState('');
  const [directionsResponse, setDirectionsResponse] = useState(null);
  const [directionsRef, setDirectionsRef] = useState(null);
  const [markers, setMarkers] = useState();
  const [donePlanning, setDonePlanning] = useState(false);

  // Controle do centro do nearbySearch
  const [nearbySearchCenter, setNearbySearchCenter] = useState();
  const [canRender, setCanRender] = useState(true);

  // Controle da InfoWindow para visualizar
  // infos do local selecionado
  const [infoWindow, setInfoWindow] = useState(false);

  // Salva os locais selecionados pelo usuário
  const [filteredPlaces, setFilteredPlaces] = useState();

  const appState = useContext(StateContext);

  //Function que lida com o clica em um marcador de comércio
  function handleFilterMarker(place) {
    setInfoWindow(place);
  }

  // Salvar trip
  const [origin, setOrigin] = useState();
  const [originLocation, setOriginLocation] = useState();
  const [destination, setDestination] = useState();
  const [destinationLocation, setDestinationLocation] = useState();
  const [spinner, setSpinner] = useState(false);

  const handleSaveTrip = () => {
    if (!directionsResponse) {
      return;
    }
    setSpinner(true);

    const trips = {
      latMatch: directionsResponse.request.origin.location.lat(),
      lngMatch: directionsResponse.request.origin.location.lng(),
      latDestiny: directionsResponse.request.destination.location.lat(),
      lngDestiny: directionsResponse.request.destination.location.lng(),
      destiny: `${origin} ; ${destination}`,
      idUser: appState.user.id,
    };

    let tripId;

    const tripService = new TripService(trips, appState.user.jwtkey);
    tripService
      .createTrip()
      .then((response) => {
        console.log('TESTE TRIP SERVICE', response);
        tripId = response.data.id;
      })
      .then(() => {
        if (filteredPlaces) {
          filteredPlaces.map((localFiltrado) => {
            const dtoFiltered = {
              localName: localFiltrado.vicinity,
              latitude: localFiltrado.latLng.lat,
              longitude: localFiltrado.latLng.lng,
              url: localFiltrado.imageURL,
              tripId,
            };

            tripService.createFilter(tripId, dtoFiltered);
          });
          setSpinner(false);
        } else {
          setSpinner(false);
        }

        toggleExampleModal();
      })
      .catch((err) => {
        setSpinner(false);
        console.error('TESTE TRIP ERROR', err);
      });
  };

  useEffect(() => {
    function defineNearbyPlaces() {
      const rota = [];
      // rota.push(`https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=${nearbySearchCenter.lat},${nearbySearchCenter.lng}&radius=${'1500'}&type=${filterSelected}&key=AIzaSyBw46FEvXL1fBBgw8bocxI-fYTcva5yTeQ`);
      rota.push(`/trip/place_location/${nearbySearchCenter.lat}/${nearbySearchCenter.lng}/1500/${filterSelected}`);

      // Axios.get('https://cors-anywhere.herokuapp.com/' + rota[0], {
      Axios.get(rota[0], { headers: { authorization: appState.user.jwtkey } })
        .then((response) => {
          setMarkers(null);
          let locais = [];

          response.data.results.forEach((local) => {
            locais.push(local);
          });

          if (filteredPlaces) {
            filteredPlaces.map((marcador) => {
              locais = locais.filter((filtrar) => {
                if (marcador.latLng.lat != filtrar.geometry.location.lat && marcador.latLng.lng != filtrar.geometry.location.lng) {
                  return filtrar;
                }
              });
            });
          }

          setMarkers(locais);
        })
        .catch((error) => {
          console.log(error);
        });
    }

    if (canRender && nearbySearchCenter && filterSelected) {
      defineNearbyPlaces();
    }

    setCanRender(false);
    setTimeout(() => {
      setCanRender(true);
    }, 500);
  }, [filterSelected, nearbySearchCenter]);

  useEffect(() => {
    navigator.geolocation.getCurrentPosition(
      (data) => {
        setUserLocation({
          lat: data.coords.latitude,
          lng: data.coords.longitude,
        });
        setNearbySearchCenter({
          lat: data.coords.latitude,
          lng: data.coords.longitude,
        });
      },
      (error) => {
        console.log(error);
      }
    );
  }, []);

  // Pega a localização do usuário e coloca o marcador no mapa
  useEffect(() => {
    if (userLocation && map) {
      map.panTo(userLocation);
      map.setZoom(16);
      setMarkerUser(userLocation);
    }
  }, [userLocation, map]);

  // Toda vez que um local for selecionado, remove o mesmo do array de nearbySearch
  useEffect(() => {
    const respostaNearbySearch = markers;
    let arrayAuxiliar = [];

    if (filteredPlaces != null) {
      filteredPlaces.map((filtered) => {
        arrayAuxiliar = respostaNearbySearch.filter((nearby) => {
          if (filtered.latLng.lat != nearby.geometry.location.lat && filtered.latLng.lng != nearby.geometry.location.lng) {
            return nearby;
          } else {
            nearby.selected = true;
            return nearby;
          }
        });
      });

      setMarkers(arrayAuxiliar);
    }
  }, [filteredPlaces]);

  const onLoad = useCallback(function callback(map) {
    setMap(map);
  }, []);

  const onClick = (e) => {
    // setMarker(e.latLng);
  };

  const onUnmount = useCallback(function callback(map) {
    setMap(null);
  }, []);

  const partidaRef = (ref) => setPartida(ref);

  const destinoRef = (ref) => setDestino(ref);

  const handlePartida = (e) => {
    const { location } = partida.getPlaces()[0].geometry;
    setOriginLocation(location);
    map.panTo(location);
    map.setZoom(16);
    setUserLocation(null);
    setUserLocation(location);
    setDirectionsOrigin(location);
  };

  const handleDestino = (e) => {
    const { location } = destino.getPlaces()[0].geometry;
    setDestinationLocation(location);
    setMarker(null);
    console.log('DIRECTIONSDESTINY', location);
    setDirectionsDestiny(location);
  };

  const directionsCallback = (response) => {
    setDirectionsDestiny('');
    setDirectionsOrigin('');
    setDirectionsResponse(response);
    console.log('DIRECTIONS RESPONSE', response);
  };

  // Lida com o clique de salvamento na Info Window
  const handleInfoWindowSave = (place) => {
    let alreadySelected = false;

    if (filteredPlaces != null) {
      filteredPlaces.map((filtered) => {
        if (place.latLng.lat === filtered.latLng.lat && place.latLng.lng === filtered.latLng.lng) {
          alreadySelected = true;
        }
      });
    }

    if (alreadySelected) {
      return;
    }

    const places = [];

    let indexer = 0;

    if (filteredPlaces != null) {
      filteredPlaces.map((filtered, ind) => {
        indexer = ind;
        filtered.id = ind;
        filtered.selected = false;
        places.push(filtered);
      });
    }

    place.id = indexer + 1;

    places.push(place);

    setFilteredPlaces(places);
  };

  const handleInfoClose = () => {
    setInfoWindow(null);
  };

  const handleRemoveCard = (item) => {
    const places = filteredPlaces;

    const removed = places.filter((place) => place.id != item.id);

    setFilteredPlaces(removed);
  };

  // código para lidar com a infowindow dos marcdores
  // de lugares filtrados
  const [infoSFilter, setInfoSFilter] = useState();

  const handleSelectedFilter = (data) => {
    setInfoSFilter(data);
  };

  const [exampleModal, toggleExampleModal] = useModali({
    animated: true,
    title: 'Pronto',
    message: 'Sua viagem foi criada com sucesso!',
    buttons: [
      <Modali.Button
        label="Ok"
        isStyleDefault
        onClick={() => {
          setDonePlanning(true);
        }}
      />,
    ],
  });

  if (donePlanning) {
    return <Redirect to="/ultimasViagens" />;
  }

  return (
    <MapWrapper>
      <LoadScript googleMapsApiKey={process.env.REACT_APP_GOOGLE_MAPS_API_KEY} libraries={libraries}>
        <div className="divider">
          <div className="formWrapper">
            <div className="form">
              <h2>Hora de planejar sua viagem.</h2>
              <div className="googleinput">
                <img src={mapmarker} alt="" />
                <StandaloneSearchBox onLoad={partidaRef} onPlacesChanged={handlePartida}>
                  <input onKeyPress={(e) => setOrigin(e.target.value)} className="autocomplete" placeholder="De onde você está partindo?" />
                </StandaloneSearchBox>
              </div>
              <div className="googleinput">
                <img src={flag} alt="" />
                <StandaloneSearchBox onLoad={destinoRef} onPlacesChanged={handleDestino}>
                  <input onKeyPress={(e) => setDestination(e.target.value)} className="autocomplete" placeholder="Para onde você esta indo?" />
                </StandaloneSearchBox>
              </div>

              <div className="filtersWrapper">
                <div
                  className="openFilters"
                  onClick={() => {
                    setFilterMenu(!filterMenu);
                  }}
                >
                  <img src={plus} alt="" />
                  <p>Definir os filtros da viagem</p>
                </div>

                <Modali.Modal {...exampleModal}></Modali.Modal>

                {filterMenu ? (
                  <form className="inputs">
                    <div className="filter">
                      <label className="radio">
                        <span className="radio__input">
                          <input
                            name="filtro"
                            onClick={() => {
                              setFilterSelected('park');
                            }}
                            type="radio"
                          />
                          <span className="radio__control"></span>
                        </span>
                      </label>
                      {/* <img src={parques} alt="" /> */}
                      <label>Parques</label>
                    </div>

                    <div className="filter">
                      <label className="radio">
                        <span className="radio__input">
                          <input
                            name="filtro"
                            onClick={() => {
                              setFilterSelected('restaurante');
                            }}
                            type="radio"
                          />
                          <span className="radio__control"></span>
                        </span>
                      </label>
                      {/* <img src={restaurante} alt="" /> */}
                      <label>Restaurantes</label>
                    </div>

                    <div className="filter">
                      <label className="radio">
                        <span className="radio__input">
                          <input
                            name="filtro"
                            onClick={() => {
                              setFilterSelected('bar');
                            }}
                            type="radio"
                          />
                          <span className="radio__control"></span>
                        </span>
                      </label>
                      {/* <img src={bares} alt="" /> */}
                      <label>Bares</label>
                    </div>

                    <div className="filter">
                      <label className="radio">
                        <span className="radio__input">
                          <input
                            name="filtro"
                            onClick={() => {
                              setFilterSelected('hotel');
                            }}
                            type="radio"
                          />
                          <span className="radio__control"></span>
                        </span>
                      </label>
                      {/* <img src={hoteis} alt="" /> */}
                      <label>Hoteis</label>
                    </div>

                    <div className="filter">
                      <label className="radio">
                        <span className="radio__input">
                          <input
                            name="filtro"
                            onClick={() => {
                              setFilterSelected('farmácia');
                            }}
                            type="radio"
                          />
                          <span className="radio__control"></span>
                        </span>
                      </label>
                      {/* <img src={farmacia} alt="" /> */}
                      <label>Farmácias</label>
                    </div>

                    <div className="filter">
                      <label className="radio">
                        <span className="radio__input">
                          <input
                            name="filtro"
                            onClick={() => {
                              setFilterSelected('hospital');
                            }}
                            type="radio"
                          />
                          <span className="radio__control"></span>
                        </span>
                      </label>
                      {/* <img src={hospitais} alt="" /> */}
                      <label>Hospitais</label>
                    </div>
                  </form>
                ) : (
                  <></>
                )}
              </div>

              <FilterList handleRemove={handleRemoveCard} placesSaved={filteredPlaces ? filteredPlaces : []} />
            </div>
          </div>

          <div className="create-travel">
            <div className="header"></div>
            <div className="buttons">
              <button onClick={handleSaveTrip} className={`confirmar ${directionsResponse ? 'habilitado' : 'nao-habilitado'}`}>
                {spinner && <img src={spinnersvg} alt="" />}
                {filteredPlaces && <img src={icon_save} alt="" />}
                {!filteredPlaces ? 'Monte seu roteiro' : 'Salvar viagem'}
              </button>
            </div>
          </div>

          <div className="map">
            <GoogleMap
              onDragEnd={(e) => {
                setNearbySearchCenter({
                  lat: map.center.lat((r) => r),
                  lng: map.center.lng((r) => r),
                });
              }}
              onClick={onClick}
              mapContainerStyle={mapContainerStyle}
              center={center}
              zoom={10}
              onLoad={onLoad}
              onUnmount={onUnmount}
              options={options}
            >
              {/* Marcador do usuário */}
              {markerUser ? <Marker title={'Você está aqui!'} animation={2} icon={you_icon} position={markerUser} /> : <></>}

              {/* Marcadores dos resultados da busca do nearbySearch */}
              {markers ? (
                markers.map((place, ind) => {
                  if (!place.selected) {
                    return (
                      <Marker
                        key={ind}
                        icon={nearby_places}
                        animation={2}
                        position={place.geometry.location}
                        onClick={() => {
                          handleFilterMarker(place);
                        }}
                      ></Marker>
                    );
                  }
                })
              ) : (
                <></>
              )}

              {/* Marcadores que foram selecionados pelo usuário */}
              {filteredPlaces &&
                filteredPlaces.map((local, index) => {
                  return (
                    <Marker
                      key={index}
                      onClick={() => {
                        handleSelectedFilter(local);
                      }}
                      animation={2}
                      icon={saved_place}
                      title={'Parada selecionada por você!'}
                      position={local.latLng}
                    />
                  );
                })}

              {infoSFilter ? <CInfoWindowFilter saveCallback={handleInfoWindowSave} handleInfoClose={handleInfoClose} data={infoSFilter}></CInfoWindowFilter> : <></>}
              {infoWindow ? <CInfoWindow saveCallback={handleInfoWindowSave} handleInfoClose={handleInfoClose} data={infoWindow}></CInfoWindow> : <></>}

              {directionsDestiny !== '' && directionsOrigin !== '' ? (
                <DirectionsService
                  callback={directionsCallback}
                  options={{
                    destination: directionsDestiny,
                    origin: directionsOrigin,
                    travelMode: 'DRIVING',
                  }}
                />
              ) : (
                <DirectionsService callback={directionsCallback} options={{}} />
              )}

              {directionsResponse != null ? (
                <DirectionsRenderer
                  directions={directionsResponse}
                  // ref={directRef}
                  // options={{
                  //   directions: directionsResponse,
                  // }}
                  // onLoad={rendererOnLoad}
                />
              ) : (
                <DirectionsRenderer options={{}} />
              )}
            </GoogleMap>
          </div>
        </div>
      </LoadScript>
    </MapWrapper>
  );
};

export default ContainerMaps;
