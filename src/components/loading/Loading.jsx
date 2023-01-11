import React from 'react'


const Loading = ({children}) => {
  return (
    <div style={{
        top:0,
        left:0
    }} className='w-100 h-100 position-fixed d-flex justify-content-center align-items-center'>
     {
        children
     }
    </div>
  )
}

export default React.memo(Loading)