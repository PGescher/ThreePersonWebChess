import { useState } from "react";
import Dialog from "./ui/dialog";
import Button from "./ui/button";

import "./selectionPrompt.css";

const SelectionPrompt = ({ title, options, onSelect }) => {
  const [open, setOpen] = useState(true);

  const handleSelect = (option) => {
    onSelect(option);
    setOpen(false);
  };

  return (
    <Dialog open={open} onClose={() => setOpen(false)}>
        <div className="dialog-title">{title}</div>
            <div className="dialog-box">
                {options.map((option, index) => (
                <Button key={index} onClick={() => handleSelect(option)}>
                    <img src={option.image} alt={option.label} className="w-16 h-16" />
                </Button>
            ))}
            </div>
    </Dialog>
  );
};

export default SelectionPrompt;
