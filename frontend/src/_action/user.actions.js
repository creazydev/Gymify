import {useSetRecoilState} from "recoil";
import {authAtom} from "../_state";

export function useUserActions () {
    const setAuth = useSetRecoilState(authAtom);

    return {
        logout
    }

    function logout() {
        setAuth(null);
    }
}
