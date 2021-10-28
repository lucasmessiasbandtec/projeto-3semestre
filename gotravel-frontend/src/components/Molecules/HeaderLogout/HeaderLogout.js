import React, { useContext, useEffect } from 'react';
import Button from '../../Atoms/Button';
import { ButtonWrapper } from '../../Organisms/ExternNavbar/ExternNavbar.style';
import { Link } from 'react-router-dom';

import DispatchContext from '../../../DispatchContext';

function HeaderLogout() {
  const appDispatch = useContext(DispatchContext);

  useEffect(() => {
    function fakeLogin() {
      const fake = {
        data: {
          email: 'henrique_barretto@out',
          id: 'asoifgasiogsaioh',
          name: 'Henrique Albuquerque',
        },
      };

      appDispatch({ type: 'login', data: fake.data });
    }
    // fakeLogin();
  }, []);

  return (
    <ButtonWrapper>
      <Link to="/auth">
        <Button text={'Entrar'} textColor={'#2D73DD'} />
      </Link>
    </ButtonWrapper>
  );
}

export default HeaderLogout;
