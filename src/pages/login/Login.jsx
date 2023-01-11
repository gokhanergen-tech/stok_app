import React, { useCallback, useState } from 'react'
import { useDispatch } from 'react-redux'
import Button from '../../components/button/Button'
import Form from '../../components/form/Form'
import InputText from '../../components/input_text/InputText'
import Spinner from '../../components/spinner/Spinner'
import { login } from '../../http/http_client'
import { getMessage, isClientMessage } from '../../http/http_utils'
import { setAuth } from '../../store/reducers/auth_reducer/auth_reducer'

import styles from './login.module.css'
const Login = () => {

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const [validations, setValidations] = useState({ username: { valid: null }, password: { valid: null } })
    const [serverMessage, setServerMessage] = useState("")

    const [isWaiting,setWaiting]=useState(false)

    const dispatch=useDispatch();

    const handleAuthentication = useCallback(async (e) => {
        e.preventDefault()
        alert(5)
        try{
            setWaiting(true)
            setServerMessage(null)
            const {data}=await login({username,password})
            
            dispatch(setAuth({...data}))
        }catch(err){
            const response=err.response;
            if(isClientMessage(response)){
                setServerMessage(getMessage(response))
            }else{
                setServerMessage("Ooopp, something got wrong!")
            }
        }finally{
            setWaiting(false)
        }
    }, [username, password, validations])

    const handleChangeUsername = useCallback((value) => {
        if (value && value.length >= 5) {
            setValidations({ ...validations, username: { ...validations.username, valid: true } })
            setUsername(value)
        } else {
            setValidations({ ...validations, username: { ...validations.username, valid: false } })
        }
    }, [validations])

    const handleChangePassword = useCallback((value) => {
        if (value && value.length >= 8) {
            setValidations({ ...validations, password: { ...validations.password, valid: true } })
            setPassword(value)
        } else {
            setValidations({ ...validations, password: { ...validations.password, valid: false } })
        }
    }, [validations])

    return (
        <div className='row justify-content-center mt-5'>
            <div className={"col-md-6 col-sm-8 col-12 rounded p-4 " + styles.wrapper_login}>
                <div className={styles.background}>

                </div>
                <Form options={{
                    onSubmit: handleAuthentication
                }}>
                    <div className="row justify-content-center">
                        <div className='col-md-10 col-12 mb-3'>
                            <h3 className="text-secondary">Sign in</h3>
                        </div>
                        <div className='col-md-10 col-12 mb-3'>
                            <label htmlFor='username' className='d-none'></label>
                            <InputText value={username}
                                onChange={handleChangeUsername}
                                id={"username"}
                                placeholder={"Username"}
                                className={"form-control " + (validations.username.valid !== null && (validations.username.valid ? "is-valid" : "is-invalid"))}></InputText>
                        </div>
                        <div className='col-md-10 col-12 mb-3'>
                            <label htmlFor='password' className='d-none'></label>
                            <InputText
                                value={password}
                                onChange={handleChangePassword}
                                type={"password"}
                                id={"Password"}
                                placeholder={"Username"}
                                className={"form-control " + (validations.password.valid !== null && (validations.password.valid ? "is-valid" : "is-invalid"))}></InputText>

                        </div>

                        <div className='col-md-10 col-12 mb-1'>
                            <div className='d-flex justify-content-end'>
                                <Button title={isWaiting?<Spinner></Spinner>:"Sign in"}></Button>
                            </div>
                        </div>

                        <div className='col-md-10 col-12 mb-1'>
                            <div className={'text-danger ' + styles.server_feedback_error}>
                                <p>
                                    {
                                        serverMessage?serverMessage:""
                                    }
                                </p>
                            </div>
                        </div>
                    </div>
                </Form>
            </div>
        </div>
    )
}

export default Login