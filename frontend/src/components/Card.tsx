import React, { useEffect, useState } from 'react'
import { Icon, Link } from '@fluentui/react';
import { initializeFileTypeIcons } from '@fluentui/react-file-type-icons';
import { getFileTypeIconProps, FileTypeIconMap } from '@fluentui/react-file-type-icons';
import { MdDelete, MdDetails, MdMoreHoriz } from 'react-icons/md';
import { CgClose, CgDetailsMore } from 'react-icons/cg';
import { BiDownload } from 'react-icons/bi';
import { Description, Dialog, DialogPanel, DialogTitle, Menu, MenuButton, MenuItem, MenuItems, Popover, PopoverButton, PopoverPanel } from '@headlessui/react';
import { api } from '../api/api';
import axios from 'axios';
import { IoInformation, IoInformationCircle } from 'react-icons/io5';
// import { FileIcon } from 'react-file-icon';
initializeFileTypeIcons();
interface CardProps {
    type: string,
    file: any
}

function Card({ type, file }: CardProps) {
    let [isOpen, setIsOpen] = useState(false)
    const handleDelete = async () => {
        await axios({
            method: "delete",
            url: api + `delete/${file.id}`,
        })
            .then(function (response) {
                console.log(response);
                window.location.reload();
            })
            .catch(function (response) {
                console.log(response);
            });
    }
    return (
        <>
            <Dialog open={isOpen} onClose={() => setIsOpen(false)} className="relative z-50">

                <div className="fixed inset-0 flex w-screen items-center justify-center p-4 bg-[#282424]/50  ">
                    <DialogPanel className="max-w-lg space-y-4 border bg-[#181414] rounded-lg  border-2 border-[#625E5E] text-white  p-12">
                        <div className='flex justify-between items-center'>
                            <DialogTitle className="font-bold">File Detials</DialogTitle>
                            <CgClose onClick={() => setIsOpen(!isOpen)} className='text-white cursor-pointer' />
                        </div>
                        <div className='group relative bg-[#282424] flex flex-col  items-center rounded-lg  p-4'>
                            <div className='p-5'>
                                <Icon {...getFileTypeIconProps({ extension: (typeof (type) == 'string') ? type : '', type: (typeof (type) == 'number') ? type : undefined, size: 96 })} />
                            </div>
                        </div>
                        <div className="flex flex-col gap-2 w-[20vw]">
                            <div>
                                <div className='text-[#625E5E] mb-1'>Name:</div>
                                <div className='font-semibold text-sm'>{file.fileName}</div>
                            </div>
                            <div>
                                <div className='text-[#625E5E] mb-1'>Type:</div>
                                <div className='font-semibold text-sm'>{type.toUpperCase()}</div>
                            </div>
                            <div>
                                <div className='text-[#625E5E] mb-1'>Total Size:</div>
                                <div className='font-semibold text-sm'>{(file.fileSize / (1024 * 1024)).toFixed(2)} MB</div>
                            </div>
                        </div>
                    </DialogPanel>
                </div>
            </Dialog>
            <div className='group relative hover:outline-4 hover:outline-offset-1 hover:outline-[#625E5E] cursor-pointer bg-[#282424] flex flex-col  items-center rounded-lg hover:bg-[#484444] p-4'>
                <div className='p-5'>
                    <Icon {...getFileTypeIconProps({ extension: (typeof (type) == 'string') ? type : '', type: (typeof (type) == 'number') ? type : undefined, size: 96 })} />
                </div>
                <div className="relative w-full mt-4">
                    <div className="items-center overflow-hidden max-w-[100%] truncate justify-self-center text-white bg-[#282424] rounded-full px-4 py-2 text-lg font-semibold">
                        {file.fileName}
                    </div>
                    <Menu as='div' className=''>
                        <MenuButton className="group absolute bottom-0 right-0 cursor-pointer p-1 rounded-full justify-self-end">
                            <MdMoreHoriz className="w-[30px] h-[30px] hover:text-white opacity-0 group-hover:opacity-100 group-hover:bg-[#a8a4a4] group-hover:text-[#d0cccc] rounded-full transition duration-200" />
                        </MenuButton>
                        <MenuItems className=' absolute z-200 right-[-15vw] text-white bg-[#181414] bottom-[25px] w-[15vw] border-2 border-[#625E5E] rounded-lg'>
                            <MenuItem>
                                <a onClick={() => setIsOpen(true)} className='p-3 flex gap-4 items-center hover:bg-[#625E5E] transition delay-100 duration-150 ease-in-out '>
                                    <IoInformationCircle />
                                    View Details
                                </a>
                            </MenuItem>
                            <MenuItem>
                                <a href={api + `download/${file.id}`} target="_blank" rel="noopener noreferrer" className='p-3 flex gap-4 items-center hover:bg-[#625E5E] transition delay-100 duration-150 ease-in-out' >
                                    <BiDownload />

                                    Download
                                </a>
                            </MenuItem>
                            <MenuItem>
                                <a onClick={handleDelete} className='p-3 flex gap-4 items-center hover:bg-[#625E5E] transition delay-100 duration-150 ease-in-out' >
                                    <MdDelete />
                                    Delete
                                </a>
                            </MenuItem>
                        </MenuItems>
                    </Menu>

                </div>
            </div >
        </>
    )
}

export default Card;