import axios from "axios"

const apiAddress=process.env.REACT_APP_REST_API_IP;

const restApi=axios.create({
    withCredentials:true,
    baseURL:apiAddress,
    headers:{
        "Content-Type":"application/json",
        "Accept":"application/json"
    }
})

const login=(credentials)=>restApi.post("/api/login",credentials)
const authControl=()=>restApi.get("/api/user");

export default restApi;

export {login,authControl}