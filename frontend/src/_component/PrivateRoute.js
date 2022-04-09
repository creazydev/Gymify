import {Navigate, Outlet} from 'react-router-dom';
import {useRecoilValue} from 'recoil';

import {authAtom} from '../_state';

export { PrivateRoute };

function PrivateRoute() {
    const auth = useRecoilValue(authAtom);
    return auth ? <Outlet /> : <Navigate to="/login" />;
}
