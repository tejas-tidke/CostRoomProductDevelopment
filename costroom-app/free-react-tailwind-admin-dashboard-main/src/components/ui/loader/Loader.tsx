import React from 'react';

interface LoaderProps {
  size?: 'sm' | 'md' | 'lg';
  className?: string;
  text?: string;
  variant?: 'default' | 'primary' | 'secondary';
}

const Loader: React.FC<LoaderProps> = ({ 
  size = 'md', 
  className = '', 
  text, 
  variant = 'default' 
}) => {
  // Define size classes
  const sizeClasses = {
    sm: 'w-4 h-4',
    md: 'w-8 h-8',
    lg: 'w-12 h-12',
  };

  // Define spinner color classes based on variant
  const variantClasses = {
    default: 'text-gray-500',
    primary: 'text-indigo-600',
    secondary: 'text-blue-500',
  };

  return (
    <div className={`flex flex-col items-center justify-center ${className}`}>
      <div
        className={`${sizeClasses[size]} ${variantClasses[variant]} animate-spin rounded-full border-2 border-solid border-current border-r-transparent align-[-0.125em] motion-reduce:animate-[spin_1.5s_linear_infinite]`}
        role="status"
      >
        <span className="!absolute !-m-px !h-px !w-px !overflow-hidden !whitespace-nowrap !border-0 !p-0 ![clip:rect(0,0,0,0)]">
          Loading...
        </span>
      </div>
      {text && (
        <p className="mt-2 text-sm font-medium text-gray-700 dark:text-gray-300">
          {text}
        </p>
      )}
    </div>
  );
};

export default Loader;