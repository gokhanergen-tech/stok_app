import {createSlice} from '@reduxjs/toolkit'


const initialState={
    isAuth:false,
    user:null,
}

const setAuthUser=(state,action)=>{
    console.log(action.payload)
   const payload=action.payload;
   state.isAuth=payload.isAuth;
   state.user=payload.user;
}

const authReducer=createSlice({
    name:"authReducer",
    initialState,
    reducers:{
       setAuth:setAuthUser
    }
})



export const {setAuth}=authReducer.actions;


export default authReducer.reducer;