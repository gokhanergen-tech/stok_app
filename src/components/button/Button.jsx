import React from 'react'
import proptypes from '../../proptype'

const Button = ({onClick,className,type,title}) => {
  return (
    <button {...(onClick?({onClick}):({}))} type={type} className={'btn btn-primary '+className}>{title}</button>
  )
}

Button.defaultProps={
    className:"",
    type:"submit",
    title:""
}

Button.propTypes={
    onClick:proptypes.func,
    className:proptypes.string,
    type:proptypes.string,
    title:proptypes.any
}

export default Button