import React from 'react';
import { ButtonWrapper } from './Button.style';

const Button = ({ text, textColor, onclick }) => (
  <ButtonWrapper onClick={onclick} color={textColor}>
    {text}
  </ButtonWrapper>
);

export default Button;
