import React from 'react';
import LabelStyle from './label.style';

const Label = ({ htmlFor, text }) => (
  <LabelStyle htmlFor={htmlFor}>{text}</LabelStyle>
);

export default Label;