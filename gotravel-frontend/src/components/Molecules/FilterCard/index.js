import React, { useEffect } from 'react';
import { Card, NoImage } from './style';

function FilterCard(props) {
  const data = props.data[0] || props.data;

  const ifNoImage = data.localName.substring(0, 4).toUpperCase();

  return (
    <Card indexator={props.key}>
      {data.imageURL ? (
        <img src={data.imageURL} className="preview" alt="" />
      ) : (
        <NoImage>
          <p>{ifNoImage[0] + ifNoImage[1] + '\n' + ifNoImage[2] + ifNoImage[3]}</p>
        </NoImage>
      )}
      <div className="content">
        <h1 className="name">{data.localName}</h1>
        {data.vicinity && <p className="endereco">{data.vicinity.length > 32 ? data.vicinity.substring(0, 32) + '...' : data.vicinity}</p>}

        <div className="buttons">
          <button
            className="remover"
            onClick={() => {
              props.handleRemove(data);
            }}
          >
            Remover Local
          </button>
        </div>
      </div>
    </Card>
  );
}

export default FilterCard;
