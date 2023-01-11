import React from 'react'
import proptypes from '../../proptype'

const Form = ({options:{
    onSubmit
},children}) => {
  return (
    <form onSubmit={onSubmit}>
       {
        children
       }
    </form>
  )
}

Form.propTypes={
    options:proptypes.shape({
        onSubmit:proptypes.func.isRequired
    }).isRequired,
    children:proptypes.any
}

Form.defaultProps={
    options:{
        onSubmit:()=>{}
    }
}

export default Form