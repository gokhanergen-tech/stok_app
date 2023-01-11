import React from 'react'
import proptypes from '../../proptype'

import styles from "./input_text.module.css"

const InputText = ({onChange,id,className,type,placeholder}) => {
  return (
    <input placeholder={placeholder} type={type} id={id} className={styles.input+" "+className} onChange={(e)=>onChange(e.target.value)}></input>
  )
}

InputText.defaultProps={
    placeholder:"",
    className:"",
    type:"text",
    id:""
}

InputText.propTypes={
    onChange:proptypes.func.isRequired,
    id:proptypes.string,
    className:proptypes.string,
    type:proptypes.string,
    placeholder:proptypes.string
}



export default React.memo(InputText)