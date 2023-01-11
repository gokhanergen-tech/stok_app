const isClientMessage=(response)=>{
    return String(response?.status).startsWith("4");
};


const getMessage=(response)=>{
    return response?.data?.message;
};

export {isClientMessage,getMessage}

