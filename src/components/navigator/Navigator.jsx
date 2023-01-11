import React from 'react'

import styles from './navigator.module.css'
const Navigator = () => {
    return (
        <div className={'bg-light ' + styles.wrapper}>
            <div className="container-lg pt-3 pb-3">
                <div className='d-flex align-items-center jusftify-content-between'>
                    <div className='ms-auto'>
                        <span role={"button"}><i className="bi bi-person-circle fs-3"></i></span>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default React.memo(Navigator)