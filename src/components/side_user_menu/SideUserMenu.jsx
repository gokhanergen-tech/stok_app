import React, { useEffect, useRef } from 'react'
import { NavLink } from 'react-router-dom';


import styles from './side_user.module.css'
const SideUserMenu = () => {

    const refWrapper = useRef(null);

    useEffect(() => {
        refWrapper.current.style.height = "calc(100vh - " + refWrapper.current.getBoundingClientRect().top + "px)"

        const scroll = () => {
            if (refWrapper.current.getBoundingClientRect().top >= 0&&window.innerWidth>768)
                refWrapper.current.style.height = "calc(100vh - " + refWrapper.current.getBoundingClientRect().top + "px)"
        }

        window.addEventListener("scroll", scroll)

        return () => {
            window.removeEventListener("scroll", scroll);
        }
    }, [])

    return (
        <div ref={refWrapper} className={'bg-light col-md-3 col-sm-4 col-12 sticky-sm-top px-4 ' + styles.wrapper}>
            <h3>Menu</h3>
            <hr></hr>
            <div>
                <NavLink className={({isActive})=>{
                    return (isActive?styles.active:"")
                }} end to={"/products"}>
                    <div role={"button"} className={'d-flex align-items-center gap-4 '}>
                  
                        <span><i className="bi bi-box"></i></span>
                        <span>Products</span>
                    </div>
                </NavLink>

                <NavLink className={({isActive})=>{
                    return (isActive?styles.active:"")
                }}  to={"/stocks"}>
                    <div role={"button"} className='d-flex align-items-center gap-4'>
                        <span><i className="bi bi-box"></i></span>
                        <span>Stocks</span>
                    </div>
                </NavLink>
            </div>
        </div>
    )
}

export default React.memo(SideUserMenu)