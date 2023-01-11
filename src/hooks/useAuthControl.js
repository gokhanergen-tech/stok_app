import { useEffect, useState } from 'react'
import { useDispatch } from 'react-redux';
import { authControl } from '../http/http_client';
import { setAuth } from '../store/reducers/auth_reducer/auth_reducer';

const useAuthControl = () => {

    const [isControlling, setControlling] = useState(true);
    const dispatch = useDispatch();

    useEffect(() => {
        (async () => {
            try {
                const { data } = await authControl();

                dispatch(setAuth(data))
            } catch (err) {
               console.log("Error!")
            } finally {
                setControlling(false);
            }
        })();
    }, [])

    return { isControlling };
}

export default useAuthControl