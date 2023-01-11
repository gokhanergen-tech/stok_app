import React from 'react'
import { Outlet } from 'react-router-dom'
import SideUserMenu from '../side_user_menu/SideUserMenu'

const LoggedBottom = () => {
    return (
        <div className='row m-0'>
            <SideUserMenu>

            </SideUserMenu>
            <div className="align-self-start col">
              <Outlet></Outlet>
            </div>
        </div>
    )
}

export default LoggedBottom