
import React from 'react';
import { useSelector } from 'react-redux';
import { Navigate, Outlet, Route, Routes } from 'react-router-dom';
import './App.css';
import Navigator from './components/navigator/Navigator';
import Login from './pages/login/Login';
import Products from './pages/products/Products';
import Stocks from './pages/stocks/Stocks';

import useAuthControl from './hooks/useAuthControl';
import Loading from './components/loading/Loading';
import Spinner from './components/spinner/Spinner';
import LoggedBottom from './components/logged_bottom/LoggedBottom';


const Main = () => {
  return <div>
    <Outlet></Outlet>
  </div>
}

const NotLogged=()=>{
  return <div className='container'>
    <Outlet></Outlet>
  </div>
}

const LoggedPage = () => {
  return <div>
    <Navigator></Navigator>
    <LoggedBottom></LoggedBottom>
  </div>
}

function App() {

  const isAuth = useSelector(state => state.authReducer.isAuth)

  const protectedPage = (Component) => isAuth ? <Component></Component> : <Navigate to={"/login"} replace={true}></Navigate>


  const nonProtectedPage = (Component) => !isAuth ? <Component></Component> : <Navigate to={"/products"} replace={true}></Navigate>

  const { isControlling } = useAuthControl();


  if (isControlling) {
    return <Loading>
      <Spinner size={45}></Spinner>
    </Loading>
  }

  return (
    <Routes>
      <Route element={<Main></Main>}>
        <Route element={<NotLogged></NotLogged>}>
          <Route path='/' element={<Navigate to="/login" replace={true}></Navigate>}></Route>
          <Route path='login' element={nonProtectedPage(Login)}></Route>
        </Route>

        <Route element={<LoggedPage></LoggedPage>}>
          <Route path='/products' element={protectedPage(Products)}>
          </Route>
          <Route path='/stocks' element={protectedPage(Stocks)}>
          </Route>
        </Route>
      </Route>

      <Route path='*' element={<h5>Not found</h5>}></Route>
    </Routes>
  );
}

export default App;
