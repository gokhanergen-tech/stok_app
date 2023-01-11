import React from 'react'
import proptypes from '../../proptype'

const Spinner = ({size=20}) => {
    return (
        <span style={{
            width:size+"px",
            height:size+"px"
        }} className="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
    )
}

Spinner.propTypes={

    size:proptypes.number
}

export default React.memo(Spinner)