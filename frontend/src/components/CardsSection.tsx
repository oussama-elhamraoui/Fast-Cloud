import React, { useEffect, useState } from 'react'
import Card from './Card'

const CardsSection = ({ document }: any) => {
    return (
        <>
            {document && !(document.length == 0) ?
                <div className='grid grid-cols-5 p-20 gap-4 justify-center'>
                    {console.log(document)}
                    {document.map((doc: any) => {
                        const fileTypeArray = doc.fileName.split('.');
                        const fileType = fileTypeArray[fileTypeArray.length - 1];
                        return <Card key={doc.id} type={fileType} file={doc} />

                    })}
                </div> :
                <div className='mt-10 justify-item-center text-center text-white font-bold text-lg'>
                    No content Found
                </div>
            }

        </>
    )
}

export default CardsSection