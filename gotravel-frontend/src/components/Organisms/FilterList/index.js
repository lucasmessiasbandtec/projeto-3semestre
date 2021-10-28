import React, { useEffect, useState } from 'react';
import FilterCard from '../../Molecules/FilterCard';

import { Container } from './styles';

import tumbleweed from '../../../assets/carro-virado.svg';
import { NothingHere } from './styles';

const FilterList = ({ placesSaved, handleRemove }) => {
  console.log(placesSaved);

  return (
    <Container>
      {/* <FilterCard
        data={{
          imageURL: '',
          latLng: '',
          localName: 'Restaurante Big Esfiha',
        }}
      /> */}
      {placesSaved.length >= 1 && <h1 className="titulo">Suas Paradas</h1>}
      {placesSaved.length >= 1 ? (
        placesSaved.map((place, ind) => {
          return <FilterCard key={ind} indexator={ind} handleRemove={handleRemove} data={place}></FilterCard>;
        })
      ) : (
        <NothingHere>
          <h1>Nada aqui.</h1>
          <h3>Você ainda não salvou nenhuma parada.</h3>
          <img src={tumbleweed} alt="" />
        </NothingHere>
      )}
    </Container>
  );
};

export default FilterList;
